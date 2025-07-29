package com.example.offline.repository.domain.org

import android.util.LruCache
import com.example.core.models.Org
import com.example.offline.extensions.launchWithMutex
import com.example.offline.repository.database.converter.combineWith
import com.example.offline.utils.DEFAULT_CACHE_SIZE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.sync.Mutex

/**
 * Repository for managing organizations in the database.
 * This repository handles the insertion and caching of organization entities.
 */
@SuppressWarnings("TooManyFunctions")
class DatabaseOrgRepository(
    private val orgDao: OrgDao,
    private val scope: CoroutineScope,
    cacheSize: Int = DEFAULT_CACHE_SIZE
) {
    private val orgCache = LruCache<String, OrgEntity>(cacheSize)
    private val mutex = Mutex()

    /**
     * Inserts a single organization into the database.
     * If the organization already exists in the cache, it will be updated.
     */
    fun insertOrg(org: Org) {
        insertListOrg(listOf(org.toEntity()))
    }

    /**
     * Inserts a collection of organizations into the database.
     * If an organization with the same cid already exists, it will be combined with the cached version.
     * If the combined organization is different from the cached version, it will be inserted into the database.
     */
    fun insertListOrg(listOrg: Collection<OrgEntity>) {
        if (listOrg.isEmpty()) return
        val orgToInsert = listOrg.filter { org ->
            val cachedOrg = orgCache.get(org.cid)
            if (cachedOrg != null) {
                val combinedOrg = cachedOrg.combineWith(org)
                if (combinedOrg != null && combinedOrg != cachedOrg) {
                    orgCache.put(org.cid, combinedOrg)
                    return@filter true // Insert the combined organization
                }
                return@filter false // No need to insert, already exists in cache
            }
            true // Insert the new organization
        }
        cacheListOrg(orgToInsert)
        scope.launchWithMutex(mutex) {
            orgDao.insertOrgs(orgToInsert)
        }
    }

    /**
     * Retrieves an organization by its cid.
     * If the organization is not found in the cache, it will be fetched from the database.
     */
    private fun cacheListOrg(listOrg: List<OrgEntity>) {
        listOrg.forEach { org ->
            orgCache.put(org.cid, org)
        }
    }
}