package com.example.offline.repository.database.converter

import com.example.core.models.Order
import com.example.offline.repository.domain.order.OrderEntity

fun OrderEntity.combineWith(
    other: OrderEntity
): OrderEntity? {
    if( this.cid != other.cid) {
        return null
    }
    return OrderEntity(
        customerId = this.customerId,
        totalAmount = this.totalAmount + other.totalAmount,
        status = if (this.status == "COMPLETED" || other.status == "COMPLETED") "COMPLETED" else "PENDING"
    )
}

fun Order.combineWith(
    other: Order
): Order? {
    if (this.orderId != other.orderId) {
        return null
    }
    return Order(
        orderId = this.orderId,
        customerId = this.customerId,
        status = if (this.status == "COMPLETED" || other.status == "COMPLETED") "COMPLETED" else "PENDING",
        totalAmount = this.totalAmount + other.totalAmount
    )
}