package com.example.client.providers

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

object RetrofitProvider {
    fun getRetrofitProvider(
        baseUrl: String,
        moshiConverter: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        // Implementation to provide a Retrofit instance
        // This could include setting up the base URL, converters, etc.
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(moshiConverter)
    }
}