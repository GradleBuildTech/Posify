package com.example.core.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class
ResponseData<T>(
    val status: Int? = null,
    val message: String? = null,
    @Json(name = "data")
    val data: T? = null,
    val errors: String? = null
)
