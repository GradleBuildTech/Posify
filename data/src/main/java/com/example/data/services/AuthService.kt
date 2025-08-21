package com.example.data.services

import com.example.core.models.User
import com.example.core.models.meta.PosTerminalAccess
import com.example.core.models.request.auth.AuthRequest
import com.example.core.models.request.auth.SignInRequest
import com.example.core.models.response.AuthResponse
import com.example.core.models.response.ResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {

    companion object {
        const val BRANCH = "/app/api/v1/users"
        const val LOGIN = "$BRANCH/login"
        const val AUTHENTICATE = "/app/api/v1/authenticate"
        const val GET_POS_TERMINAL_ACCESS = "$BRANCH/posTerminalIdAccess"
    }

    @POST(AUTHENTICATE)
    suspend fun authenticate(
        @Body authRequest: AuthRequest
    ): Response<AuthResponse>

    @POST(LOGIN)
    suspend fun login(
       @Body signInRequest: SignInRequest
    ): Response<ResponseData<User>>

    @GET("$GET_POS_TERMINAL_ACCESS/{userId}/{orgId}")
    suspend fun getPosTerminalAccess(
        @Path("userId") userId: Int,
        @Path("orgId") orgId: Int
    ): Response<ResponseData<List<PosTerminalAccess>>>
}