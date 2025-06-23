package com.example.client.providers

import com.example.client.services.RefreshTokenService
import retrofit2.Retrofit

object ApiServiceProvider {
    fun getRefreshTokenService(retrofit: Retrofit): RefreshTokenService {
        return retrofit.create(RefreshTokenService::class.java) // Assuming the same service for refresh token
    }
}