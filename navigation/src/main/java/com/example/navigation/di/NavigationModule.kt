package com.example.navigation.di

import com.example.navigation.NavigationService
import com.example.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object NavigationModule {

    @Provides
    fun provideNavigationGraph(navigator: Navigator): NavigationService = navigator
}