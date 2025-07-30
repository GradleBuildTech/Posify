package com.example.core.models.meta

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tennant(
    val id: Int? = null,
    val code: String? = null,
    val name: String? = null,
    val domainUrl: String? = null,
    val taxCode: String? = null,
    val imageId: Int? = null,
    val industryId: Int? = null
)