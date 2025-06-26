package com.example.offline.repository.domain.order

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@SuppressWarnings("TooManyFunctions")
@Dao
internal interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<OrderEntity>)

    @Query("DELETE FROM orders WHERE cid = :cid")
    suspend fun deleteOrder(cid: String)


    @Query("SELECT * FROM orders WHERE cid = :cid")
    suspend fun getOrderByCid(cid: String): OrderEntity?

    @Query("SELECT * FROM orders")
    suspend fun getAllOrders(): List<OrderEntity>
}
