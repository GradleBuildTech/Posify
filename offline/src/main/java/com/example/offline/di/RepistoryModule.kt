package com.example.offline.di

import com.example.offline.repository.domain.order.DatabaseOrderRepository
import com.example.offline.repository.domain.order.OrderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

// Provides the repository for order-related operations
@Module
@InstallIn(SingletonComponent::class)
internal class RepistoryModule {
    @Provides
    fun provideOrderRepository(orderDao: OrderDao): DatabaseOrderRepository {
        return DatabaseOrderRepository(
            orderDao, CoroutineScope(Dispatchers.IO)
        )
    }

}

