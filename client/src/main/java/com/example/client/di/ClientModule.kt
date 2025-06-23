package com.example.client.di

import com.example.client.token.TokenManager
import com.example.client.token.TokenManagerImpl
import com.example.offline.dataStore.SecureTokenLocalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class ClientModule {

    @Provides
    fun bindTokenManager(
        secureTokenLocalService: SecureTokenLocalService
    ): TokenManager = TokenManagerImpl(
        secureTokenLocalService = secureTokenLocalService
    )
}