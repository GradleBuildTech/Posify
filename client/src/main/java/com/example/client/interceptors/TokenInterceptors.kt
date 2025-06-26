package com.example.client.interceptors

import com.example.client.security.SecureTokenLocalService
import com.example.client.token.RefreshTokenRequest
import com.example.client.services.RefreshTokenService
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 * OkHttp Interceptor that adds Authorization header with Bearer token and handles token refresh.
 * @param secureTokenLocalService Provides access to stored tokens.
 * @param moshi For JSON serialization/deserialization.
 * @param refreshTokService Retrofit instance for token refresh API calls.
 */
class TokenInterceptor @Inject constructor(
    private val secureTokenLocalService: SecureTokenLocalService,
    private val moshi: Moshi,
    private val refreshTokService: RefreshTokenService
) : Interceptor {
    private val lock = Any() // Synchronization object for thread safety
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    /**
     * Intercepts the request to add Authorization header and handle token refresh on 401/403 responses.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Use synchronous token retrieval to avoid runBlocking
        val token = secureTokenLocalService.getAccessTokenSync()
            ?: return chain.proceed(originalRequest)

        // Add Authorization header
        val request = originalRequest.addBearerToken(token)
        val response = chain.proceed(request)

        // Handle unauthorized or forbidden responses
        if (response.code == 401 || response.code == 403) {
            return handleUnauthorizedResponse(chain, originalRequest, token, response)
        }

        return response
    }

    /**
     * Handles 401/403 responses by refreshing the token and retrying the request.
     */
    private fun handleUnauthorizedResponse(
        chain: Interceptor.Chain,
        originalRequest: Request,
        originalToken: String,
        originalResponse: Response
    ): Response {
        synchronized(lock) {
            // Check if token has changed since initial request
            val currentToken = secureTokenLocalService.getAccessTokenSync()
            if (currentToken != originalToken && currentToken != null) {
                // Token was refreshed by another request, retry with new token
                return chain.proceed(originalRequest.addBearerToken(currentToken))
            }

            // Attempt to refresh token
            val newToken = runBlocking { refreshTokenCall() }
            if (newToken.isNullOrEmpty()) {
                return originalResponse // Refresh failed
            }

            // Save new token asynchronously
            coroutineScope.launch {
                secureTokenLocalService.saveToken(newToken)
            }

            // Retry request with new token
            return chain.proceed(originalRequest.addBearerToken(newToken))
        }
    }

    /**
     * Refreshes the token using the refresh token API.
     * @return The new access token or null if refresh fails.
     */
    private suspend fun refreshTokenCall(): String? {
        val refreshToken = secureTokenLocalService.getRefreshTokenSync() ?: return null
        if (refreshToken.isEmpty()) return null

        return try {
            val response = refreshTokService.refreshToken(RefreshTokenRequest(refreshToken))
            if (response.isSuccessful) {
                val newToken = response.body()?.accessToken ?: return null
                val newRefreshToken = response.body()?.refreshToken
                coroutineScope.launch {
                    secureTokenLocalService.saveToken(newToken)
                    if (!newRefreshToken.isNullOrEmpty()) {
                        secureTokenLocalService.saveRefreshToken(newRefreshToken)
                    }
                }
                newToken
            } else {
                null
            }
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Adds Bearer token to the Authorization header.
     */
    private fun Request.addBearerToken(token: String): Request {
        return newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
    }
}
