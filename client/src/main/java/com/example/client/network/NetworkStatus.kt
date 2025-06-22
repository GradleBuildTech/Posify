package com.example.client.network

/**
 * Represents the network status of the client application.
 *
 * @property INITIAL The initial state before any network connection is established.
 * @property CONNECTED Indicates that the client is connected to a network.
 * @property DISCONNECTED Indicates that the client is not connected to any network.
 */
enum class NetworkStatus {
    INITIAL,
    CONNECTED,
    DISCONNECTED
}