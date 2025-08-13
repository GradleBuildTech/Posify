package com.example.offline.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.offline.repository.domain.order.OrderDao
import com.example.offline.repository.domain.order.OrderEntity
import com.example.offline.repository.domain.org.OrgDao
import com.example.offline.repository.domain.org.OrgEntity
import com.example.offline.repository.domain.user.UserDao
import com.example.offline.repository.domain.user.UserEntity

@Database(
    entities = [OrderEntity::class, OrgEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase: RoomDatabase() {
    abstract fun orderDao(): OrderDao

    abstract fun orgDao(): OrgDao

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}