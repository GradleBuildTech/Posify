package com.example.client.network

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

/** * Provides network state information for the client application.
 *
 * This class monitors the network connectivity status and maintains a set of available networks.
 * It is designed to be used in conjunction with a [ConnectivityManager] to listen for network changes.
 *
 * @property coroutine The coroutine scope used for asynchronous operations.
 * @property connectivityManager The system service that provides information about network connectivity.
 * * @constructor Creates a new instance of [NetworkStateProvider].
 */
class NetworkStateProvider(
    private val context: Context,
    private val coroutine: CoroutineScope,
    private val connectivityManager: ConnectivityManager,
) {

    // A lock object to synchronize access to the network state.
    // This is used to ensure thread safety when modifying the listeners set.
    private val lock = Any()

    /** A set of currently available networks. */
    private val availableNetwork: MutableSet<Network> = mutableSetOf()

    /** Indicates whether the device is currently connected to a network. */
    private var isConnected: Boolean = false

    /** A set of listeners that will be notified of network state changes. */
    private var listeners: Set<NetworkStateListener> = setOf()

    /** A flag indicating whether the network callback is registered. */
    private val isRegistered: AtomicBoolean = AtomicBoolean(false)

    /** A callback that listens for network changes and notifies listeners of connectivity changes. */
    private val callBack: ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                availableNetwork.add(network)
                notifierNetworkStateChange()
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                notifierNetworkStateChange()
            }

            override fun onLost(network: Network) {
                availableNetwork.remove(network)
                notifierNetworkStateChange()
            }
        }

    /** * Gets the [ConnectivityManager] system service.
     *
     * @return The [ConnectivityManager] instance used to manage network connectivity.
     */
   fun getConnectivityManager(): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    /** * Gets the currently active network.
     *
     * @return The [Network] instance representing the currently active network, or null if no network is active.
     */
    fun getActiveNetwork(): Network? {
        return getConnectivityManager().activeNetwork
    }

    /** * Checks if the device is currently connected to a network.
     *
     * This method checks the network capabilities of the active network to determine if it has internet access.
     *
     * @return True if the device is connected to a network with internet access, false otherwise.
     */
    private fun notifierNetworkStateChange() {
        val isNowConnected = isConnected()
        if( isNowConnected) {
            isConnected = true
            listeners.notifyConnected()
        } else {
            isConnected = false
            listeners.notifyDisconnected()
        }
    }

    /** * Notifies all registered listeners that the network is connected.
     *
     * This method is called when the network state changes to connected.
     * It launches a coroutine to notify each listener asynchronously.
     */
    private fun Set<NetworkStateListener>.notifyConnected() {
       coroutine.launch {
           forEach { it.onConnected() }
       }
    }

    /** * Notifies all registered listeners that the network is disconnected.
     *
     * This method is called when the network state changes to disconnected.
     * It launches a coroutine to notify each listener asynchronously.
     */
    private fun Set<NetworkStateListener>.notifyDisconnected() {
        coroutine.launch {
            forEach { it.onDisconnected() }
        }
    }

    /** * Checks if the device is currently connected to a network with internet access.
     *
     * This method checks the network capabilities of the active network to determine if it has internet access.
     * It requires the `ACCESS_NETWORK_STATE` permission to function correctly.
     *
     * @return True if the device is connected to a network with internet access, false otherwise.
     */
    fun isConnected(): Boolean {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_NETWORK_STATE
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            return false
        }

        return try {
            val networkCapabilities =
                getConnectivityManager().getNetworkCapabilities(getActiveNetwork())

            networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } catch (_: Exception) {
            false
        }
        return true
    }


    /** * Registers a listener to receive network state changes.
     *
     * This method adds the specified listener to the set of listeners and registers the network callback
     * if it is not already registered.
     *
     * @param listener The [NetworkStateListener] to register.
     * How to use:
     * ```kotlin
     * val networkStateProvider = NetworkStateProvider(context, coroutine, connectivityManager)
     * networkStateProvider.registerNetworkStateListener(object : NetworkStateProvider.NetworkStateListener {
     *    override suspend fun onConnected() {
     *    // Handle network connected state
     *    }
     *    override suspend fun onDisconnected() {
     *    // Handle network disconnected state
     *    }
     *   })
     */
    fun registerNetworkStateListener(listener: NetworkStateListener) {
        synchronized(lock) {
            listeners = listeners + listener
            if (isRegistered.compareAndSet(false, true)) {
                connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), callBack)
            }
        }
    }

    /** * Unregisters a listener from receiving network state changes.
     *
     * This method removes the specified listener from the set of listeners and unregisters the network callback
     * if there are no more listeners registered.
     *
     * @param listener The [NetworkStateListener] to unregister.
     */
    fun unregisterNetworkStateListener(listener: NetworkStateListener) {
        synchronized(lock) {
            listeners = listeners - listener
            if (listeners.isEmpty() && isRegistered.compareAndSet(true, false)) {
                connectivityManager.unregisterNetworkCallback(callBack)
            }
        }
    }

    /** * Interface for listening to network state changes.
     *
     * Implement this interface to receive notifications when the network state changes.
     * The methods will be called on the coroutine scope provided to the [NetworkStateProvider].
     */
    interface NetworkStateListener {
        suspend fun onConnected()
        suspend fun onDisconnected()
    }
}