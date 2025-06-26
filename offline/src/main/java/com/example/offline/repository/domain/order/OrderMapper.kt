package com.example.offline.repository.domain.order

import com.example.core.models.Order

/**
 * Mapper to convert between OrderEntity and Order model.
 */

fun OrderEntity.toModel(orderEntity: OrderEntity): Order {
    return Order(
        orderEntity.cid,
        orderEntity.customerId,
        orderEntity.status ,
        orderEntity.totalAmount
    )
}

fun Order.toEntity(): OrderEntity {
    return OrderEntity(
        customerId = this.customerId,
        totalAmount = this.totalAmount,
        status = this.status
    ).apply {
        cid = "%s:%s".format(customerId, status)
    }
}