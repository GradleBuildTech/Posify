package com.example.offline.di

import com.example.offline.repository.domain.order.DatabaseOrderRepository
import com.example.offline.repository.domain.order.OrderDao
import com.example.offline.repository.domain.org.DatabaseOrgRepository
import com.example.offline.repository.domain.org.OrgDao
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

    // Add more repository providers here as needed
    @Provides
    fun provideOrgRepository(orgDao: OrgDao): DatabaseOrgRepository {
        return DatabaseOrgRepository(
            orgDao, CoroutineScope(Dispatchers.IO)
        )
    }
}

