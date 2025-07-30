package com.example.onboarding.auth.controller

enum class AuthStateUiState {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR
}

fun AuthStateUiState.isLoading(): Boolean {
    return this == AuthStateUiState.LOADING
}