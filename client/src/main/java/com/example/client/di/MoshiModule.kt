package com.example.client.di

import com.example.client.providers.moshi.MoshiBuilderProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class MoshiModule {
    @Provides
    fun provideMoshi(): Moshi = MoshiBuilderProvider.moshiBuilder.build()
}