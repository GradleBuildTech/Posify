package com.example.core.modes

import com.squareup.moshi.Json


data class ErrorResponse(
    @Json(name = "message")
    val message: String? = null,
)
