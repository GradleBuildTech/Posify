package com.example.core.models

class Order {
    var orderId: String = ""
    var customerId: String = ""
    var status: String = ""
    var totalAmount: Double = 0.0


    constructor(orderId: String, customerId: String, status: String, totalAmount: Double) {
        this.orderId = orderId
        this.customerId = customerId
        this.status = status
        this.totalAmount = totalAmount
    }

    override fun toString(): String {
        return "Order(orderId='$orderId', customerName='$customerId', orderDate='$status', totalAmount=$totalAmount)"
    }
}