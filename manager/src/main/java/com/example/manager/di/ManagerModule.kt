package com.example.manager.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object ManagerModule {
    // This module is currently empty but can be used to provide dependencies related to the manager feature.
    // Future implementations can include providing ViewModels, repositories, or other dependencies needed for the manager feature.
    //    @Provides
    //    @Singleton
    //    fun provideAuthManager(): AuthManager = AuthManager()
}