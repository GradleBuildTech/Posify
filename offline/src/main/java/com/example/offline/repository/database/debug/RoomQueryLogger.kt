package com.example.offline.repository.database.debug

import android.util.Log
import androidx.sqlite.db.SupportSQLiteQuery

import com.example.offline.repository.AppDatabase
import com.example.offline.repository.domain.order.OrderEntity
import com.example.offline.repository.domain.order.OrderDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
internal class RoomQueryLogger @Inject constructor(appDatabase: AppDatabase) {

    private val orderDao: OrderDao = appDatabase.orderDao()

    suspend fun logQuery(query: SupportSQLiteQuery) {
        withContext(Dispatchers.IO) {
            Log.d("RoomQueryLogger", "Executing query: ${query.sql}")
            val result = orderDao.getAllOrders()
            Log.d("RoomQueryLogger", "Query result: $result")
        }
    }

    suspend fun logInsert(order: OrderEntity) {
        withContext(Dispatchers.IO) {
            orderDao.insertOrder(order)
            Log.d("RoomQueryLogger", "Inserted order: $order")
        }
    }
}