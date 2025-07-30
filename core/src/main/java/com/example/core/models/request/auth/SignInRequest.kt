package com.example.core.models.request.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInRequest(
    val userName: String? = null,
    val password: String? = null,
    val googleId: String? = null,
    val zaloId: String? = null,
    val facebookId: String? = null
)