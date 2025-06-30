package com.example.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {
    @Provides
    @MainThreadScope
    fun provideMainThreadDispatcher(): CoroutineScope {
        return CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
    }
}

