package com.example.client.services

import com.example.client.models.query.token.RefreshTokenRequest
import com.example.client.response.RefreshTokenResponse
import com.example.client.utils.ApiPath
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {
    @POST("${ApiPath.AUTH}/refresh-token")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<RefreshTokenResponse>
}