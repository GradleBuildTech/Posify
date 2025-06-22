package com.example.client.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.client.interceptoors.TokenInterceptor
import com.example.client.services.RefreshTokenService
import com.example.offline.dataStore.SecureTokenLocalService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
     * Provides an OkHttp interceptor that adds a "Content-Type" header to each request.
     * This is useful for ensuring that the server knows the format of the data being sent.
     * This interceptor is applied to all requests made by the OkHttpClient.
     * @return An instance of [Interceptor] that adds the header.
     */
    @Provides
    @HeaderInterceptorAnnotation
    fun provideHeaderInterceptor(): Interceptor =
        Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }


    /**
     * Provides an OkHttp interceptor that adds a Bearer token to the Authorization header
     * and handles token refresh on 401/403 responses.
     * This interceptor is used to ensure that all requests include the necessary authentication token.
     */
    @Provides
    @TokenInterceptorAnnotation
    fun provideTokenInterceptor(
        moshi: Moshi,
        secureTokenLocalService: SecureTokenLocalService,
        refreshTokenService: RefreshTokenService
    ): Interceptor = TokenInterceptor(
        secureTokenLocalService = secureTokenLocalService,
        moshi = moshi,
        refreshTokService = refreshTokenService
    )

    @Provides
    fun provideCache(@ApplicationContext context: android.content.Context): Cache {
        // Define cache size and directory
        val cacheSize = 10L * 1024 * 1024 // 10 MB
        val cacheDirectory = java.io.File(context.cacheDir, "http_cache")
        return Cache(cacheDirectory, cacheSize)
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
        loggingInterceptor: HttpLoggingInterceptor
    ): okhttp3.OkHttpClient {
        return okhttp3.OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .cache(cache)
            .connectTimeout(duration = Duration.ofSeconds(30))
            .readTimeout(duration = Duration.ofSeconds(30))
            .build()
    }
}