package com.example.core.models.response

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "message")
    val message: String? = null,
)
