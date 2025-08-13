package com.example.offline.repository.domain.order

import androidx.collection.LruCache
import com.example.core.models.Order
import com.example.offline.extensions.launchWithMutex
import com.example.offline.repository.database.converter.combineWith
import com.example.offline.utils.DEFAULT_CACHE_SIZE

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.sync.Mutex

// Repository for managing orders in the database.
@SuppressWarnings("TooManyFunctions")
internal class DatabaseOrderRepository(
    private val orderDao: OrderDao,
    private val scope: CoroutineScope,
    cacheSize: Int = DEFAULT_CACHE_SIZE
) {
    private val orderCache = LruCache<String, Order>(cacheSize)
    private val mutex = Mutex()

    fun insertOrder(order: Order) {
        insertOrders(listOf(order))
    }

    /**
     * Inserts a collection of orders into the database.
     * If an order with the same cid already exists, it will be combined with the cached version.
     * If the combined order is different from the cached version, it will be inserted into the database.
     */
    fun insertOrders(orders: Collection<Order>) {
        if(orders.isEmpty()) return
        val updatedOrders = orders.map { order ->
            orderCache[order.orderId]?.let { cached -> order.combineWith(cached) ?: order } ?: order
        }
        val orderToInsert = updatedOrders.filter { order ->
            orderCache[order.orderId] != order
        }.map { it.toEntity() }
        cacheOrder(updatedOrders)
        scope.launchWithMutex(mutex) {
            orderToInsert.takeUnless { it.isEmpty() }?.let {
                orderDao.insertOrders(it)
            }
        }
    }

    /**
     * Retrieves an order by its cid.
     * If the order is not found in the cache, it will be fetched from the database.
     */
    private fun cacheOrder(orderEntity: List<Order>) {
        orderEntity.forEach { order ->
            orderCache.put(order.orderId, order)
        }
    }

    /**
     * Retrieves an order by its cid.
     * If the order is not found in the cache, it will be fetched from the database.
     */
    fun deleteOrder (cid: String) {
        orderCache.remove(cid)
        scope.launchWithMutex(mutex) { orderDao.deleteOrder(cid) }
    }

}