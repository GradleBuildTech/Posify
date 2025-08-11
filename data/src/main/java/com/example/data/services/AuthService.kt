package com.example.data.services

import com.example.core.models.User
import com.example.core.models.request.auth.AuthRequest
import com.example.core.models.request.auth.SignInRequest
import com.example.core.models.response.AuthResponse
import com.example.core.models.response.ResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    companion object {
        const val BRANCH = "/app/api/v1/users"
        const val LOGIN = "$BRANCH/login"
        const val AUTHENTICATE = "/app/api/v1/authenticate"
    }

    @POST(AUTHENTICATE)
    suspend fun authenticate(
        @Body authRequest: AuthRequest
    ): Response<AuthResponse>

    @POST(LOGIN)
    suspend fun login(
       @Body signInRequest: SignInRequest
    ): Response<ResponseData<User>>
}