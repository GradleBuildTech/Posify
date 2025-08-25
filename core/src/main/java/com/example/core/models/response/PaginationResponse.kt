package com.example.core.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaginationResponse<T>(
    val status: Int? = null,
    val message: String? = null,
    val errors: String? = null,
    val totalPages: Int? = null,
    val currentPage: Int? = null,
    val pageSize: Int? = null,
    val totalItems: Int? = null,
    @Json(name = "data")
    val data: List<T> = emptyList()
)