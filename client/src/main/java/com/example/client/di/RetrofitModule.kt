package com.example.client.di

import com.example.client.di.tag.RefreshTokeRetrofit
import com.example.client.providers.ApiServiceProvider
import com.example.client.providers.ConverterFactoryProvider
import com.example.client.providers.RetrofitProvider
import com.example.client.services.RefreshTokenService
import com.example.posNativeApp.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
internal class RetrofitModule {

    /**
     * Provides the base URL for the Retrofit client.
     * This URL is used as the root for all API requests.
     */
    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL


    /**
     * Provides a [Converter.Factory] for Retrofit using Moshi.
     * This factory is used to convert JSON responses into Kotlin objects.
     *
     * @param moshi The Moshi instance used for JSON serialization/deserialization.
     * @return A [Converter.Factory] that can be used with Retrofit.
     */
    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): Converter.Factory =
        ConverterFactoryProvider.getMoshiConverterFactory(moshi)

    /**
     * Provides a [Retrofit] instance configured with the base URL,
     * Moshi converter factory, and OkHttp client.
     * This instance is used to make API calls.
     * @param baseUrl The base URL for the API.
     * @param moshiConverterFactory The Moshi converter factory for JSON serialization/deserialization.
     * @param okHttpClient The OkHttp client used for making network requests.
     */
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        moshiConverterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return RetrofitProvider.getRetrofitProvider(
            baseUrl = baseUrl,
            moshiConverter = moshiConverterFactory,
            okHttpClient = okHttpClient
        ).build()
    }

    // 🔄 Retrofit for refreshing tokens
    @Provides
    @RefreshTokeRetrofit
    fun provideRefreshRetrofit(
        moshiConverterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(moshiConverterFactory)
        .build()

    /**
     * Provides an OkHttpClient instance.
     * This client is used for making network requests.
     * You can customize it with interceptors, timeouts, etc. if needed.
     */

    @Provides
    fun provideRefreshTokenService(@RefreshTokeRetrofit retrofit: Retrofit): RefreshTokenService =
        ApiServiceProvider.getRefreshTokenService(retrofit)
}