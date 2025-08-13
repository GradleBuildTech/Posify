package com.example.onboarding.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.token.TokenManager
import com.example.manager.auth.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * SplashController is responsible for managing the splash screen logic,
 * including checking for an existing token and updating the authentication state.
 * It uses the TokenManager to determine if a token exists and updates the AuthManager accordingly.
 */
@HiltViewModel
class SplashController @Inject constructor(
    private val tokenManager: TokenManager,
): ViewModel() {

    fun checkToken() {
        viewModelScope.launch {
            //Delay for splash screen effect
            kotlinx.coroutines.delay(2000) // Simulate a 2-second splash screen delay
            if (tokenManager.isAuthenticated()) {
                Log.d("SplashController", "Token found, authenticating user")
                AuthManager.authenticate("")
            } else {
                Log.d("SplashController", "No token found, setting unauthenticated state")
                AuthManager.setUnauthenticated("No token found")
            }
        }
    }
}