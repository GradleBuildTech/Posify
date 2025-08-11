package com.example.onboarding.auth.controller

import com.example.core.internal.machine.ViewModelMachine
import com.example.core.models.User
import com.example.domain.usecase.auth.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModelMachine<AuthState, AuthEvent>(
    initialState = AuthState()
) {
    override suspend fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignIn -> {
                handleSignIn(event.username, event.password, event.domainUrl)
            }
            AuthEvent.NavigateToMain -> {

            }
        }
    }

    private suspend fun handleSignIn(username: String, password: String, domainUrl: String) {
        setUiState {
            copy(
                uiState = AuthStateUiState.LOADING,
            )
        }
        signInUseCase.invoke(
            email = username,
            password = password,
            domainUrl = domainUrl
        ).collect { either ->
            if(either.isLeft()) {
                return@collect setUiState {
                    copy(
                        uiState = AuthStateUiState.ERROR,
                        errorMessage = either.leftValue()?.errorMessage
                    )
                }
            }
            either.rightValue()?.let { user ->
                saveInformation(user)
            } ?: run {
                setUiState {
                    copy(uiState = AuthStateUiState.ERROR)
                }
            }
        }
    }

    private fun saveInformation(user: User) {

    }
}