package com.example.offline.di

import android.content.Context
import androidx.room.Room
import com.example.offline.repository.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context = context,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    ).build()

    @Provides
    fun provideOrderDao(appDatabase: AppDatabase) = appDatabase.orderDao()

    @Provides
    fun provideOrgDao(appDatabase: AppDatabase) = appDatabase.orgDao()
}