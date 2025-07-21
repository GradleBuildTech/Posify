package com.example.onboarding.auth.controller

import com.example.core.internal.machine.ViewModelMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModelMachine<AuthState, AuthEvent>(
    initialState = AuthState.IDLE
) {
    override suspend fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignIn -> {
                handleSignIn(event.username, event.password)
            }
            AuthEvent.NavigateToMain -> {
            }
        }
    }

    private fun handleSignIn(username: String, password: String) {
        // Handle sign-in logic here
    }

}