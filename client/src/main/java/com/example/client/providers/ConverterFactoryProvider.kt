package com.example.client.providers
import com.squareup.moshi.Moshi
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory

/*
* This file is part of the "Client" module of the "Example" project.
* It provides a factory for creating Moshi-based Retrofit converters.
* The factory uses the Moshi instance to create a Converter.Factory that can be used with Retrofit.
* How to use:
* 1. Ensure you have the Moshi dependency in your project.
* 2. Call `ConverterFactoryProvider.getMoshiConverterFactory(moshi)` to get the converter factory.
* 3. Use this factory when building your Retrofit instance.
* Example:
* ```kotlin
* val moshi: Moshi = MoshiBuilderProvider.moshiBuilder.build()
* val converterFactory = ConverterFactoryProvider.getMoshiConverterFactory(moshi)
* val retrofit = Retrofit.Builder()
*    .baseUrl("https://api.example.com/")
*   .addConverterFactory(converterFactory)
*   .build()
*  ```
 */
object ConverterFactoryProvider {
    fun getMoshiConverterFactory(moshi: Moshi): Converter.Factory {
        return MoshiConverterFactory.create(moshi)
    }
}