package com.example.data.repositories.impl

import com.example.core.models.User
import com.example.core.models.meta.PosTerminalAccess
import com.example.core.models.request.auth.AuthRequest
import com.example.core.models.request.auth.SignInRequest
import com.example.core.models.response.AuthResponse
import com.example.core.models.response.ResponseData
import com.example.data.extensions.runCatchingApiCall
import com.example.data.repositories.AuthRepositories
import com.example.data.services.AuthService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoriesImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepositories{
    override suspend fun login(signInRequest: SignInRequest): Flow<ResponseData<User>> =
        runCatchingApiCall {
            authService.login(signInRequest)
        }

    override suspend fun authenticate(authRequest: AuthRequest): Flow<AuthResponse> =
        runCatchingApiCall {
            authService.authenticate(authRequest)
        }

    override suspend fun getPosTerminalAccess(
        userId: Int,
        orgId: Int
    ): Flow<ResponseData<List<PosTerminalAccess>>> =
        runCatchingApiCall {
            authService.getPosTerminalAccess(userId, orgId)
        }
}