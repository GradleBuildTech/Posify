package com.example.client.services

import retrofit2.Response
import retrofit2.http.GET


interface AuthService {
    @GET("auth")
    suspend fun authenticate(): Response<*>
}