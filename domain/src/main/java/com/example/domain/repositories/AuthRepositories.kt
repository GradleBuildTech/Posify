package com.example.domain.repositories

import com.example.core.models.User
import com.example.core.models.request.auth.SignInRequest
import com.example.core.models.response.ResponseData
import kotlinx.coroutines.flow.Flow

interface AuthRepositories {
    suspend fun login(signInRequest: SignInRequest): Flow<ResponseData<User>>
}