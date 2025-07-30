package com.example.data.di

import com.example.data.providers.ApiServiceProvider
import com.example.data.services.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    /**
     * Provides an OkHttpClient instance.
     * This client is used for making network requests.
     * You can customize it with interceptors, timeouts, etc. if needed.
     */
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService =
        ApiServiceProvider.getAuthService(retrofit)

    @Provides
    fun provideMetaService(retrofit: Retrofit): com.example.data.services.MetaService =
        ApiServiceProvider.getMetaService(retrofit)
}


