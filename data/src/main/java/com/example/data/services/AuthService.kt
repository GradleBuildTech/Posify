package com.example.data.services

import com.example.core.models.User
import com.example.core.models.request.auth.SignInRequest
import com.example.core.models.response.ResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    companion object {
        const val BRANCH = "/app/api/v1/users"
        const val LOGIN = "$BRANCH/login"
    }

    @GET("auth")
    fun authenticate(): Response<*>

    @POST(LOGIN)
    fun login(
       @Body signInRequest: SignInRequest
    ): Response<ResponseData<User>>


}