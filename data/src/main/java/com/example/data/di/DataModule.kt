package com.example.data.di

import com.example.data.repositories.AuthRepositoriesImpl
import com.example.data.repositories.MetaRepositoriesImpl
import com.example.data.services.AuthService
import com.example.data.services.MetaService
import com.example.domain.repositories.AuthRepositories
import com.example.domain.repositories.MetaRepositories
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class DataModule {
    @Provides
    fun providesMetaRepository(metaService: MetaService): MetaRepositories {
        return MetaRepositoriesImpl(metaService)
    }

    @Provides
    fun providesAuthRepository(authService: AuthService): AuthRepositories {
        return AuthRepositoriesImpl(authService)
    }
}