package com.example.data.di

import com.example.data.repositories.AuthRepositories
import com.example.data.repositories.FloorRepositories
import com.example.data.repositories.MetaRepositories
import com.example.data.repositories.TableRepositories
import com.example.data.repositories.impl.AuthRepositoriesImpl
import com.example.data.repositories.impl.FloorRepositoriesImpl
import com.example.data.repositories.impl.MetaRepositoriesImpl
import com.example.data.repositories.impl.TableRepositoriesImpl
import com.example.data.services.AuthService
import com.example.data.services.FloorService
import com.example.data.services.MetaService
import com.example.data.services.TableService
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

    @Provides
    fun providesTableRepository(tableService: TableService): TableRepositories {
        return TableRepositoriesImpl(tableService)
    }

    @Provides
    fun providesAnotherRepository(floorService: FloorService): FloorRepositories {
        return FloorRepositoriesImpl(floorService)
    }
}