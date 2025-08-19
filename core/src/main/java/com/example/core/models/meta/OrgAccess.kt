package com.example.core.models.meta

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrgAccess(
    val isActive: String? = null,
    val tenantId: Int? = null,
    val userId: Int? = null,
    val orgId: Int? = null,
    @Json(name = "name")
    val orgName: String? = null,
    val orgWards: String? = null,
    val orgPhone: String? = null,
    val orgCode: String? = null,
    val isAssign: String? = null
)