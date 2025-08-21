package com.example.domain.di

import com.example.client.security.SecureTokenLocalService
import com.example.core.di.IODispatcher
import com.example.data.repositories.AuthRepositories
import com.example.data.repositories.MetaRepositories
import com.example.domain.usecase.auth.AuthSaveUser
import com.example.domain.usecase.auth.SaveInformation
import com.example.domain.usecase.auth.SignInUseCase
import com.example.offline.repository.domain.org.DatabaseOrgRepository
import com.example.offline.repository.domain.user.DatabaseUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Module for providing domain layer dependencies.
 * This module is responsible for providing use cases and other domain-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
internal class DomainModule {
    @Provides
    fun provideSignInUseCase(
        metaRepositories: MetaRepositories,
        authRepositories: AuthRepositories,
        orgRepositories: DatabaseOrgRepository,
        secureTokenLocalService: SecureTokenLocalService,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): SignInUseCase {
        return SignInUseCase(
            authRepositories = authRepositories,
            metaRepositories = metaRepositories,
            orgRepositories = orgRepositories,
            secureTokenLocalService = secureTokenLocalService,
            ioDispatcher = ioDispatcher
        )
    }

    @Provides
    fun providesAuthSaveUserUseCase(
        databaseUserRepository: DatabaseUserRepository
    ): AuthSaveUser {
        return AuthSaveUser(databaseUserRepository)
    }


    @Provides
    fun provideSaveInformationUseCase(
        orgDataBase: DatabaseOrgRepository,
        secureTokenLocalService: SecureTokenLocalService
    ): SaveInformation {
        return SaveInformation(
            orgDataBase = orgDataBase,
            secureTokenLocalService = secureTokenLocalService
        )
    }

    @Provides
    fun provideGetPosTerminalAccessUseCase(
        authRepositories: AuthRepositories,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): com.example.domain.usecase.auth.GetPosTerminalAccess {
        return com.example.domain.usecase.auth.GetPosTerminalAccess(
            authRepositories = authRepositories,
            ioDispatcher = ioDispatcher
        )
    }
}