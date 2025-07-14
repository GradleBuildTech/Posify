package com.example.navigation.graph

import com.example.navigation.utils.AppDecorator
import com.example.navigation.utils.WithoutArgsScreen

object Splash: WithoutArgsScreen() {
    override val route = AppDecorator.SPLASH

    // Splash screen does not require any arguments, so we can keep it simple.
    // The actual implementation of the splash screen will be handled in the UI layer.
}