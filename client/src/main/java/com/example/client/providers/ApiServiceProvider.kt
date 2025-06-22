package com.example.client.providers

import com.example.client.services.AuthService
import com.example.client.services.RefreshTokenService
import retrofit2.Retrofit

object ApiServiceProvider {
    fun getAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    fun getRefreshTokenService(retrofit: Retrofit): RefreshTokenService {
        return retrofit.create(RefreshTokenService::class.java) // Assuming the same service for refresh token
    }
}