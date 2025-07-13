package com.example.client.manager.auth

/**
 * Represents the state of authentication in the application.
 * This sealed class defines three possible states:
 * - [Authenticated]: The user is authenticated, containing the user's ID.
 * - [Unauthenticated]: The user is not authenticated, optionally containing a reason for the unauthenticated state.
 * - [Authenticating]: The user is in the process of authenticating, containing a progress indicator.
 */
sealed class AuthState {
    data class Authenticated(val userId: String) : AuthState()
    data class Unauthenticated(val reason: String? = null) : AuthState()
    data class Authenticating(val progress: Int = 0) : AuthState()
}