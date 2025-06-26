package com.example.client.di

import android.content.Context
import com.example.client.AppClientState
import com.example.client.network.NetworkMonitor
import com.example.client.security.SecureTokenLocalService
import com.example.client.token.TokenManager
import com.example.client.token.TokenManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ClientModule {

    @Provides
    fun provideSecureTokenLocalService(
        @ApplicationContext context: Context
    ): SecureTokenLocalService {
        return SecureTokenLocalService(context)
    }

    @Provides
    fun provideTokenManager(
        secureTokenLocalService: SecureTokenLocalService
    ): TokenManager = TokenManagerImpl(
        secureTokenLocalService = secureTokenLocalService
    )


    @Provides
    @Singleton
    fun provideAppClientState(
        netWorkMonitor: NetworkMonitor
    ) :  AppClientState = AppClientState(netWorkMonitor)
}