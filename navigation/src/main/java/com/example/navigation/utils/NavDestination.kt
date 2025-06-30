package com.example.navigation.utils

import androidx.navigation.NavBackStackEntry


/**
 * Represents a navigation destination that requires an argument of type [T].
 * This interface extends [NodeScreen] and provides methods to define the destination route
 * and parse the argument from a [NavBackStackEntry].
 * @param T The type of the argument required by the destination.
 */
typealias DestinationRoute = String

interface NavDestination<T> {
    // The route for the destination, which is a string representation.
    fun destination(arg: T): DestinationRoute

    // Parses the argument from the NavBackStackEntry.
    fun objectParser(entry: NavBackStackEntry): T
}