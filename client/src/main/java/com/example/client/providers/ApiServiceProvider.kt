package com.example.client.providers

import com.example.client.services.AuthService
import retrofit2.Retrofit

object ApiServiceProvider {
    fun getAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}