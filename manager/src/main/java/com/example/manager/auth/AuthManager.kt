package com.example.manager.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * AuthManager.kt
 *
 * This file is part of the client application.
 *
 * It manages the authentication state of the user, providing methods to authenticate,
 * set unauthenticated state, and check if the user is authenticated.
 */
object AuthManager {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unknown)
    val authState: StateFlow<AuthState> = _authState


    //Authenticates the user and updates the auth state
    fun authenticate(userId: String) {
        _authState.value = AuthState.Authenticated(userId)
    }

    //Sets the auth state to unauthenticated with an optional reason
    fun setUnauthenticated(reason: String? = null) {
        _authState.value = AuthState.Unauthenticated(reason)
    }

    //Checks if the user is authenticated
    fun isAuthenticated(): Boolean {
        return _authState.value is AuthState.Authenticated
    }

    //Gets the user ID if authenticated, otherwise returns null
    fun getUserId(): String? {
        return (_authState.value as? AuthState.Authenticated)?.userId
    }

    //Listens for authentication state changes
    suspend fun observeAuthState(onChange: (AuthState) -> Unit) {
        _authState.collect { state ->  onChange(state) }
    }
}