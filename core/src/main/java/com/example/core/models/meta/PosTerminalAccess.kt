package com.example.core.models.meta

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PosTerminalAccess(
    val tenantId: Int? = null,
    val posTerminalId: Int? = null,
    val orgId: Int? = null,
    val isActive: String? = null,
    val name: String? = null
)