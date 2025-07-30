package com.example.onboarding.auth.controller

sealed class AuthEvent {
    data class SignIn(
        val username: String,
        val password: String,
        val domainUrl: String,
    ) : AuthEvent()
    data object NavigateToMain : AuthEvent()
}