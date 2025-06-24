package com.example.client.socket.event

sealed class OrderSocketEvent {
    data class OrderCreated(val orderId: String) : OrderSocketEvent()
    data class OrderUpdated(val orderId: String, val status: String) : OrderSocketEvent()
    data class OrderCancelled(val orderId: String) : OrderSocketEvent()
    data class Error(val errorMessage: String) : OrderSocketEvent()
}