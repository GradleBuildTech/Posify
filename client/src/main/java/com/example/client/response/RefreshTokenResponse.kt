package com.example.client.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)