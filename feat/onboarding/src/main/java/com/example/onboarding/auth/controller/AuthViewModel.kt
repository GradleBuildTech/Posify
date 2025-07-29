package com.example.onboarding.auth.controller

import com.example.client.security.SecureTokenLocalService
import com.example.core.internal.machine.ViewModelMachine
import com.example.navigation.core.NavigationService
import com.example.offline.repository.domain.org.DatabaseOrgRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    /**
     * [orgDatabaseRepository] is used to interact with the organization database.
     */
    private val orgDatabaseRepository: DatabaseOrgRepository,

    /**
     * [secureTokenLocalService] is used to manage secure tokens locally.
     */
    private val secureTokenLocalService: SecureTokenLocalService
) : ViewModelMachine<AuthStateUiState, AuthEvent>(
    initialState = AuthStateUiState.IDLE
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