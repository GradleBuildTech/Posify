package com.example.offline.di

import android.content.Context
import com.example.offline.dataStore.SecureTokenLocalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Provides
    fun provideSecureTokenLocalService(
        @ApplicationContext context: Context
    ): SecureTokenLocalService {
        return SecureTokenLocalService(context)
    }
}