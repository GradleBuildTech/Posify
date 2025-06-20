package com.example.client.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal interface  RetrofitModule {

//    @Provides
//    fun provideBaseUrl() = BuildCo.

    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }
}