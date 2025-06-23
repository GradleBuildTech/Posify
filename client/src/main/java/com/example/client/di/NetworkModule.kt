package com.example.client.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.client.di.tag.MainThreadScope
import com.example.client.network.NetworkMonitor
import com.example.client.network.NetworkStateProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    /**
     * Provides a singleton instance of [NetworkStateProvider].
     * This provider is responsible for monitoring network connectivity and notifying listeners of changes.
     *
     * @param context The application context used to access system services.
     * @return An instance of [NetworkStateProvider].
     */
    @Provides
    @Singleton
    fun provideNetworkStateProvider(
        @ApplicationContext context: Context,
        @MainThreadScope coroutine: CoroutineScope
    ): NetworkStateProvider {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return NetworkStateProvider(
            context = context,
            coroutine = coroutine,
            connectivityManager = cm
        )
    }

    /**
     * Provides a singleton instance of [NetworkMonitor].
     * This monitor uses the [NetworkStateProvider] to track network connectivity status.
     *
     * @param networkStateProvider The provider used to monitor network state changes.
     * @return An instance of [NetworkMonitor].
     */
    @Provides
    @Singleton
    fun provideNetworkMonitor(
        networkStateProvider: NetworkStateProvider
    ): NetworkMonitor = NetworkMonitor(networkStateProvider)

}