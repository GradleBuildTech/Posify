package com.example.client.socket.module

import com.example.client.socket.SocketFactory
import com.example.client.socket.StreamSocketEvent
import com.example.client.socket.StreamWebSocket
import com.example.client.socket.event.OrderSocketEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.concurrent.atomic.AtomicBoolean

/**
 * OrderSocket is a WebSocket client for handling order-related events.
 * It connects to a WebSocket server, listens for incoming messages,
 * and allows sending order-related messages.
 * * Usage:
 * * 1. Create an instance of `OrderSocket` with a `SocketFactory` and connection arguments.
 * * 2. Call `start()` to initiate the connection.
 * * 3. Use `sendOrderCreated` and `sendOrderCancelled` to send messages.
* */
class OrderSocket(
    private val socketFactory: SocketFactory,
    private val connectionArg: SocketFactory.ConnectionArg
) {
    private val _socketEvent = MutableSharedFlow<OrderSocketEvent>()
    val socketEvent = _socketEvent.asSharedFlow()

    private var webSocket: StreamWebSocket? = null
    val isConnected: AtomicBoolean = AtomicBoolean(false)
    private var reconnectJob: Job? = null

    private var scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun start() {
        if (isConnected.get()) return
        isConnected.set(true)
        reconnectJob = scope.launch {
            try {
                connect()
            } catch (e: Exception) {
                _socketEvent.emit(OrderSocketEvent.Error("Connection failed: ${e.message}"))
                delay(300)
            }
        }
    }

    suspend fun connect() {
        webSocket = socketFactory.createSocket(connectionArg)
        webSocket?.listen()?.collect { event ->
            when (event) {
                is StreamSocketEvent.Error -> {
                    isConnected.set(false)
                    _socketEvent.emit(OrderSocketEvent.Error("WebSocket error: ${event.error.message}"))
                    reconnect()
                }
                is StreamSocketEvent.Message ->{
                    val orderEvent = parseMessage(event.message)
                    if (orderEvent != null) {
                        _socketEvent.emit(orderEvent)
                    } else {
                        _socketEvent.emit(OrderSocketEvent.Error("Unknown message format: ${event.message}"))
                    }
                }
            }
        }
    }

    fun sendOrderCreated(orderId: String, items: List<String>) {
        val message = JSONObject().apply {
            put("type", "order_created")
            put("data", JSONObject().apply {
                put("orderId", orderId)
                put("items", items)
            })
        }.toString()

        webSocket?.send(message)
    }

    fun sendOrderCancelled(orderId: String) {
        val message = JSONObject().apply {
            put("type", "order_cancelled")
            put("data", JSONObject().apply {
                put("orderId", orderId)
            })
        }.toString()

        webSocket?.send(message)
    }

    //Update the parseMessage function to handle the new message format
    private fun parseMessage(message: String): OrderSocketEvent? {
        return try {
            val json = JSONObject(message)
            val type = json.getString("type")
            val data = json.getJSONObject("data")

            when (type) {
                "order_created" -> OrderSocketEvent.OrderCreated(
                    orderId = data.getString("orderId"),
                )
                "order_cancelled" -> OrderSocketEvent.OrderCancelled(
                    orderId = data.getString("orderId")
                )
                "error" -> OrderSocketEvent.Error(data.optString("message"))
                else -> null
            }
        } catch (e: Exception) {
            OrderSocketEvent.Error("Parse error: ${e.message}")
        }
    }

    private fun reconnect() {
        if (!isConnected.get()) {
            start()
        }
    }

    fun stop() {
        isConnected.set(false)
        webSocket?.close()
        reconnectJob?.cancel()
        webSocket = null
    }
}