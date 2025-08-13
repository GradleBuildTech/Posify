package com.example.client.token

interface TokenManager {
    /**
     * Retrieves the current access token.
     *
     * @return The current access token as a String.
     */
    fun getAccessToken(): String

    /**
     * Sets the access token.
     *
     * @param accessToken The access token to set.
     */
    suspend fun setAccessToken(accessToken: String)

    /**
     * Clears the access token.
     */
    fun clearAccessToken()

    /**
     * Retrieves the current refresh token.
     *
     * @return The current refresh token as a String.
     */
    fun getRefreshToken(): String

    /**
     * Sets the refresh token.
     *
     * @param refreshToken The refresh token to set.
     */
    suspend fun setRefreshToken(refreshToken: String)


    /**
     * Clears the refresh token.
     */
    fun clearRefreshToken()

    fun hasToken(): Boolean

    fun isAuthenticated(): Boolean {
        return getAccessToken().isNotEmpty() && getRefreshToken().isNotEmpty()
    }
}