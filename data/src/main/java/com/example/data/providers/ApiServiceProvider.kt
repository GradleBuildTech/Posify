package com.example.data.providers

import com.example.data.services.AuthService
import retrofit2.Retrofit

object ApiServiceProvider {
    fun getAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java) // Assuming the same service for refresh token
    }
}