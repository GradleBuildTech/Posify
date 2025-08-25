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

//Default Floor.default
val Floor.default: Floor
    get() = Floor(
        id = 0,
        floorNo = "default",
        name = "Tất cả",
        description = "Mặc định",
        orgId = -1,
        isActive = "Y",
        qtyTables = 0,
        displayIndex = 0
    )