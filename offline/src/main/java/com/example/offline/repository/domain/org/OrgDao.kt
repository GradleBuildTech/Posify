package com.example.offline.repository.domain.org

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@SuppressWarnings("TooManyFunctions")
@Dao
interface OrgDao {
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertOrg(org: OrgEntity)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertOrgs(listOrg: List<OrgEntity>)

    @Query("DELETE FROM orgs WHERE cid = :cid")
    suspend fun deleteOrg(cid: String)

    @Query("SELECT * FROM orgs WHERE cid = :cid")
    suspend fun getOrgByCid(cid: String): OrgEntity?

    @Query("SELECT * FROM orgs")
    suspend fun getAllOrgs(): List<OrgEntity>
}