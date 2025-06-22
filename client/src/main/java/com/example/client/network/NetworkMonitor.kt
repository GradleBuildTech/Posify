package com.example.client.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/* * NetworkMonitor.kt
 *
 * This file is part of the client application.
 *
 * It monitors the network status and provides updates on connectivity changes.
 * It uses the NetworkStateProvider to listen for network state changes and emits the current network status.
 */

class NetworkMonitor(
    private val networkProvider: NetworkStateProvider,
) {
    private val _networkStatus: MutableStateFlow<NetworkStatus> = MutableStateFlow(NetworkStatus.INITIAL)

    val networkStatus: StateFlow <NetworkStatus> = _networkStatus

    /* Init listener */
    private val listener: NetworkStateProvider.NetworkStateListener = object : NetworkStateProvider.NetworkStateListener {
        override suspend fun onConnected() {
            _networkStatus.emit(NetworkStatus.CONNECTED)
        }

        override suspend fun onDisconnected() {
            _networkStatus.emit(NetworkStatus.DISCONNECTED)
        }
    }

    init {
        networkProvider.registerNetworkStateListener(listener)
    }

    private fun getInitialStatus(): NetworkStatus {
        return if (networkProvider.isConnected()) NetworkStatus.CONNECTED else NetworkStatus.DISCONNECTED
    }

    fun stop() {
        networkProvider.unregisterNetworkStateListener(listener)
    }

}