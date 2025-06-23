package com.example.client.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.client.interceptors.TokenInterceptor
import com.example.client.services.RefreshTokenService
import com.example.offline.dataStore.SecureTokenLocalService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class InterceptorModule {
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


    /**
     * Provides a cache for OkHttp requests.
     * This cache is used to store responses and reduce network usage.
     * The cache size is set to 10 MB and stored in the application's cache directory.
     * @param context The application context used to access the cache directory.
     * @return An instance of [Cache] configured with the specified size and directory.
     */
    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache {
        // Define cache size and directory
        val cacheSize = 10L * 1024 * 1024 // 10 MB
        val cacheDirectory = java.io.File(context.cacheDir, "http_cache")
        return Cache(cacheDirectory, cacheSize)
    }


    /**
     * Provides an OkHttp interceptor that handles offline caching.
     * This interceptor checks network availability and applies appropriate cache control headers
     * to requests when the device is offline.
     * @param context The application context used to check network status.
     * @return An instance of [Interceptor] that manages offline caching.
     */
    @Provides
    @OfflineInterceptorAnnotation
    fun provideOfflineInterceptor(
       @ApplicationContext context: Context
    ): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if(!isNetworkAvailable(context)) {
                val cacheControl = CacheControl.Builder()
                    .onlyIfCached() // Use cache only if available
                    .maxStale(7, timeUnit = TimeUnit.DAYS) // Allow cache to be stale for 7 days
                    .build()

                // Apply cache control to the request
                request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
            }
            chain.proceed(request)
        }
    }


    /**
     * Provides an OkHttp interceptor that handles online caching.
     * This interceptor applies cache control headers to responses when the device is online,
     * allowing responses to be cached for a specified duration.
     * @return An instance of [Interceptor] that manages online caching.
     */
    @Provides
    @OnlineInterceptorAnnotation
    fun provideOnlineInterceptor() : Interceptor {
        return Interceptor{ chain ->
            val response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
//                .maxAge(5, TimeUnit.MINUTES) // Cache responses for 5 minutes
//                .build()
                .noCache()
                .build()

            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .removeHeader("Pragma") // Remove Pragma header to avoid conflicts
                .build()
        }
    }


    private fun isNetworkAvailable(context: Context): Boolean {
        return try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } catch (_: Exception) {
            false
        }
        return true
    }

}