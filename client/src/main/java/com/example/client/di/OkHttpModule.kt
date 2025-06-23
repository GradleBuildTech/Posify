package com.example.client.di

import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.time.Duration

@Module
@InstallIn(SingletonComponent::class)
class OkHttpModule {
    /**
     * Provides an OkHttpClient instance with logging capabilities.
     * This client is used for making network requests.
     */
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    /**
     * Provides an OkHttpClient instance configured with a logging interceptor.
     * This client is used for making network requests.
     * Requires API level 26 (Android O) or higher due to the use of java.time.Duration.
     */
    @Provides
    @RequiresApi(Build.VERSION_CODES.O)
    fun provideOkHttpClient(
        cache: Cache,
        @HeaderInterceptorAnnotation headerInterceptor: Interceptor,
        @TokenInterceptorAnnotation tokenInterceptor: Interceptor,
        @OnlineInterceptorAnnotation onlineInterceptor: Interceptor,
        @OfflineInterceptorAnnotation offlineInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): okhttp3.OkHttpClient {
        return okhttp3.OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(offlineInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .cache(cache)
            .connectTimeout(duration = Duration.ofSeconds(30))
            .readTimeout(duration = Duration.ofSeconds(30))
            .build()
    }
}