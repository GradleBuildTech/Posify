package com.example.offline.di

import android.content.Context
import com.example.offline.dataStore.SecureTokenLocalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideSecureTokenLocalService(
        @ApplicationContext context: Context
    ): SecureTokenLocalService {
        return SecureTokenLocalService(context)
    }
}