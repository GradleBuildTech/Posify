package com.example.core.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Table(
    val id: Int? = null,
    val isActive: String? = null,
    val tableNo: String? = null,
    val name: String? = null,
    val tableStatus: String? = null,
    val orgId: Int? = null,
    val description: String? = null,
    val displayIndex: Int? = null,
    val numberSeats: Int? = null,
    val floor: Floor? = null,
    val floorId: Int? = null,
    val posOrderId: Int? = null,
    val nameReservation: String? = null,
    val customerName: String? = null,
    val customerId: Int? = null,
    val userId: Int? = null,
    val isOccupied: String? = null,
    val orderGuest: String? = null
)