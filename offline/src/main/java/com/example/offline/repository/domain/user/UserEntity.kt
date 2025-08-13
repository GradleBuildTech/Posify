package com.example.offline.repository.domain.user

import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val USER_TABLE_NAME = "users"

@Entity(tableName = USER_TABLE_NAME)
data class UserEntity(
    val id: Int? = null,
    val fullName: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val userName: String? = null,
    val isFirstLogin: String? = null, // Assuming this is a string representation of an enum
    val tenantId: String? = null,
    val tenantName: String? = null,
    val code: String? = null,
    val domainUrl: String? = null,
    val taxCode: String? = null,
    val industryId: String? = null
) {
    @PrimaryKey
    var cid: String = "%s:%s".format(id, tenantId ?: "default")
}