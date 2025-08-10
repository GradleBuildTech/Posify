package com.example.core.models.request.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthRequest(
    @Json(name = "d_org_id")
    val orgId: Int? = null,
    @Json(name = "d_tenant_id")
    val tenantId: Int? = null,
    val language: String = "vi",
    val password: String = "WebService",
    val username: String = "WebService"
)
