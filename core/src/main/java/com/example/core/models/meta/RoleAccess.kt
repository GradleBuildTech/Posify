package com.example.core.models.meta

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoleAccess(
    val tenantId: Int? = null,
    val userId: Int? = null,
    val roleId: Int? = null,
    val isActive: String? = null,
    val name: String? = null,
    val code: String? = null,
    val routeFunction: String? = null
)