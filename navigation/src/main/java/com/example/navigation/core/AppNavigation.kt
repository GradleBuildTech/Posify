package com.example.navigation.core

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigation.utils.AppDecorator
import kotlinx.coroutines.flow.collectLatest
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.onboarding.auth.AuthScreen
import com.example.auth.TestOrderScreen
import com.example.auth.TestProductDetailScreen
import com.example.manager.auth.AuthManager
import com.example.manager.auth.AuthState
import com.example.navigation.graph.DetailGraph
import com.example.onboarding.splash.SplashScreen

// This file defines the main navigation host for the application.
@Composable
fun AppNavigation(
    navigator: Navigator,
) {
    val navController = rememberNavController()
    val actions = navigator.actions
    val authState = AuthManager.authState.collectAsState()

    /**
     * This LaunchedEffect block observes the authentication state and navigates
     * to the appropriate screen based on whether the user is authenticated or not.
     * If the user is authenticated, it navigates to the order screen,
     * if not, it navigates to the auth screen.
     */
    LaunchedEffect(authState.value) {
        Log.d("AppNavigation", "Auth state changed: ${authState.value}")
        when(authState.value) {
            is AuthState.Authenticated -> {
                // User is authenticated, proceed with navigation
                if (navController.currentDestination?.route == AppDecorator.AUTH) {
                    navController.navigate(AppDecorator.ORDER) {
                        popUpTo(AppDecorator.AUTH) { inclusive = true }
                    }
                }
            }
            is AuthState.Unauthenticated -> {
                // User is unauthenticated, navigate to auth screen
                if (navController.currentDestination?.route != AppDecorator.AUTH) {
                    navController.navigate(AppDecorator.AUTH) {
                        popUpTo(AppDecorator.ORDER) { inclusive = true }
                    }
                }
            }
            else -> {
                // Handle other states if necessary
            }
        }
    }

    LaunchedEffect(actions) {
        actions.collectLatest { action ->
            when (action) {
                is Navigator.NavigationActions.Navigate -> {
                    navController.navigate(action.destination, builder = action.navOptions)
                }
                is Navigator.NavigationActions.Back -> {
                    navController.popBackStack()
                }
            }
        }
    }


    NavHost(
        navController = navController,
        startDestination = DetailGraph.startDestination
    ) {
        composable(DetailGraph.splash.route) {
            SplashScreen()
        }
        composable(DetailGraph.auth.route) {
            AuthScreen(
                openSignUp =  {
                    navController.navigate(DetailGraph.order.route)
                }
            )
        }
        composable(DetailGraph.order.route) {
            TestOrderScreen()
        }
        composable(DetailGraph.productDetail.route, arguments = DetailGraph.productDetail.arguments) {
            val productId = DetailGraph.productDetail.objectParser(it)
            TestProductDetailScreen(productId)
        }
    }
}