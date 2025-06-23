package com.example.data.services

import retrofit2.Response
import retrofit2.http.GET

interface AuthService {
    @GET("auth")
    suspend fun authenticate(): Response<*>
}