package com.example.core.models

import com.example.core.models.meta.OrgAccess
import com.example.core.models.meta.PosTerminalAccess
import com.example.core.models.meta.RoleAccess
import com.example.core.models.meta.Tennant
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val id: Int? = null,
    val fullName: String? = null,
    val phone: String? = null,
    val userName: String? = null,
    val email: String? = null,
    val jwtToken: String? = null,
    val refreshToken: String? = null,
    val tenant: Tennant? = null,
    val isFirstLogin: String? = null,
    val orgAccess: List<OrgAccess> = emptyList(),
    val roleAccess: List<RoleAccess> = emptyList(),
    val posTerminalAccess: List<PosTerminalAccess> = emptyList()
)