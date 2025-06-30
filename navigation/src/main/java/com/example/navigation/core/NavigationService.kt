package com.example.navigation.core

import androidx.navigation.NavOptionsBuilder

/**
 * NavigationService interface defines the contract for navigation operations in the application.
 * It allows navigating to different destinations and going back in the navigation stack.
 */
interface  NavigationService{
    fun navigateTo(destination: String, navOptions: NavOptionsBuilder.() -> Unit = {})
    fun goBack()
}