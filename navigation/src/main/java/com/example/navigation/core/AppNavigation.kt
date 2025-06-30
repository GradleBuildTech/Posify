package com.example.navigation.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.example.navigation.graph.DetailScreen
import com.example.navigation.utils.AppDecorator
import kotlinx.coroutines.flow.collectLatest
import androidx.navigation.compose.NavHost
import com.example.navigation.graph.detailGraph

// This file defines the main navigation host for the application.
@Composable
fun AppNavigation(
    navigator: Navigator,
    detailScreen: DetailScreen,
) {
    val navController = rememberNavController()
    val actions = navigator.actions
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
        startDestination = AppDecorator.AUTH
    ) {
        detailGraph(screens = detailScreen)
    }
}