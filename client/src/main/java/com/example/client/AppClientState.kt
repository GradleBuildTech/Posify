package com.example.client

import com.example.client.network.NetworkMonitor
import com.example.client.network.NetworkStatus
import com.example.core.models.User

// This class holds the state of the application client, including the current user and network status.
// It provides methods to set the current user and check network connectivity.
class AppClientState(
    val netWorkMonitor: NetworkMonitor,
) {
    var currentUser: User? = null

    val isConnected: Boolean
        get() = netWorkMonitor.networkStatus.value== NetworkStatus.CONNECTED

}
