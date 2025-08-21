package com.example.data.repositories

import com.example.core.models.User
import com.example.core.models.meta.PosTerminalAccess
import com.example.core.models.request.auth.AuthRequest
import com.example.core.models.request.auth.SignInRequest
import com.example.core.models.response.AuthResponse
import com.example.core.models.response.ResponseData
import kotlinx.coroutines.flow.Flow

interface AuthRepositories {
    suspend fun login(signInRequest: SignInRequest): Flow<ResponseData<User>>


    suspend fun authenticate(
        authRequest: AuthRequest
    ): Flow<AuthResponse>

    suspend fun getPosTerminalAccess(
        userId: Int,
        orgId: Int
    ): Flow<ResponseData<List<PosTerminalAccess>>>
}