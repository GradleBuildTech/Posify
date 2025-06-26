package com.example.client.token

import com.example.client.security.SecureTokenLocalService
import javax.inject.Inject

/**
 * TokenManagerImpl is responsible for managing access and refresh tokens.
 * It interacts with the SecureTokenLocalService to store and retrieve tokens.
 * How to use:
 * ```kotlin
 * val tokenManager: TokenManager = TokenManagerImpl(secureTokenLocalService)
 * * // Set access token
 * * tokenManager.setAccessToken("your_access_token")
 */
internal class TokenManagerImpl @Inject constructor(
    private val secureTokenLocalService: SecureTokenLocalService
): TokenManager {


    override fun getAccessToken(): String {
        return secureTokenLocalService.getAccessTokenSync() ?: throw IllegalStateException("Access token not found")
    }

    override suspend fun setAccessToken(accessToken: String) {
        secureTokenLocalService.saveToken(accessToken)
    }

    override fun clearAccessToken() { }

    override  fun getRefreshToken(): String {
        return secureTokenLocalService.getRefreshTokenSync() ?: throw IllegalStateException("Refresh token not found")
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        secureTokenLocalService.saveRefreshToken(refreshToken = refreshToken)
    }

    override fun clearRefreshToken() {}

    override fun hasToken(): Boolean {
        return !secureTokenLocalService.getAccessTokenSync().isNullOrEmpty()
    }
}