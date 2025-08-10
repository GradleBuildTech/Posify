package com.example.core.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    val jwtToken: String? = null,
)