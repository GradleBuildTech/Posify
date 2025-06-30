package com.example.navigation.utils

import androidx.navigation.NamedNavArgument

/**
 * Represents a screen in the navigation graph.
 * Each screen has a route and a list of arguments that can be passed to it.
 * This interface is used to define the screens in the app.
 * * Example:
 * * ```kotlin
 * * object SignIn : NodeScreen {
 * *     override val route = "signIn"
 * *     override val arguments = SignInArgs.navArguments
 * * }
 */

interface NodeScreen {
    val route: String
    /// The route is a string that represents the path to the screen.
    val arguments: List<NamedNavArgument>
}