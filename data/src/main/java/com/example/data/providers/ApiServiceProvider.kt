package com.example.data.providers

import com.example.data.services.AuthService
import com.example.data.services.FloorService
import com.example.data.services.MetaService
import com.example.data.services.TableService
import retrofit2.Retrofit

object ApiServiceProvider {
    fun getAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java) // Assuming the same service for refresh token
    }

    fun getMetaService(retrofit: Retrofit): MetaService {
        return retrofit.create(MetaService::class.java)
    }

    fun getFloorService(retrofit: Retrofit): FloorService {
        return retrofit.create(FloorService::class.java) // Assuming the same service for floor operations
    }

    fun getTableService(retrofit: Retrofit): TableService {
        return retrofit.create(TableService::class.java) // Assuming the same service for table operations
    }
}