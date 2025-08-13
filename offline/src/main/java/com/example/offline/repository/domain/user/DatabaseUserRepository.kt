package com.example.offline.repository.domain.user

import android.util.LruCache
import com.example.core.models.User
import com.example.offline.extensions.launchWithMutex
import com.example.offline.repository.database.converter.combineWith
import com.example.offline.utils.DEFAULT_CACHE_SIZE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.sync.Mutex

// Repository for managing users in the database.
@SuppressWarnings("TooManyFunctions")
internal class DatabaseUserRepository(
    private val userDao: UserDao,
    private val scope: CoroutineScope,
    cacheSize: Int = DEFAULT_CACHE_SIZE
) {
    private val userCache = LruCache<Int, User>(cacheSize)
    private val mutex = Mutex()

    fun insertUser(user: User) {
        insertUsers(listOf(user))
    }


    /**
     * Inserts a collection of users into the database.
     * If a user with the same id already exists, it will be combined with the cached version.
     * If the combined user is different from the cached version, it will be inserted into the database.
     */
    fun insertUsers(users: Collection<User>) {
        if (users.isEmpty()) return
        val updatedUsers = users.map { user ->
            userCache[user.id]?.let { cached -> user.combineWith(cached) ?: user } ?: user
        }
        val usersToInsert = updatedUsers.filter { user ->
            userCache[user.id] != user
        }.map { it.toEntity() }
        cacheUser(updatedUsers)
        scope.launchWithMutex(mutex) {
            usersToInsert.takeUnless { it.isEmpty() }?.let {
                userDao.insertUsers(it)
            }
        }
    }

    /**
     * Retrieves a user by its id.
     * If the user is not found in the cache, it will be fetched from the database.
     */
    private fun cacheUser(userEntity: List<User>) {
        userEntity.filter { it.id != null }.forEach { user ->
            userCache.put(user.id, user)
        }
    }

    /**
     * Retrieves a user by its id.
     * If the user is not found in the cache, it will be fetched from the database.
     */
    fun deleteUser(id: Int) {
        userCache.remove(id)
        scope.launchWithMutex(mutex) {
            userDao.deleteUser(id.toInt())
        }
    }
}