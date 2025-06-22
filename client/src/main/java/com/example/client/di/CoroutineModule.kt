package com.example.client.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
class CoroutineModule {

    @Provides
    @MainThreadScope
    fun provideMainThreadDispatcher(): CoroutineScope {
        return CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
    }
}