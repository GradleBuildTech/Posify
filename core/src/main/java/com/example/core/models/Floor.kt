package com.example.core.models
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Floor(
    val id: Int? = null,
    val floorNo: String? = null,
    val name: String? = null,
    val description: String? = null,
    val orgId: Int? = null,
    val isActive: String? = null,
    val qtyTables: Int? = null,
    val displayIndex: Int? = null
)