package com.example.offline.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.offline.repository.domain.order.OrderDao
import com.example.offline.repository.domain.order.OrderEntity

@Database(
    entities = [OrderEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase: RoomDatabase() {
    abstract fun orderDao(): OrderDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}