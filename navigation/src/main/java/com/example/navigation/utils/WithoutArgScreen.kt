package com.example.navigation.utils

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry


/**
 * Represents a navigation destination that does not require any arguments.
 * This interface extends [NodeScreen] and provides methods to define the destination route
 * and parse the argument from a [NavBackStackEntry].
 * This is used for screens that do not require any arguments to be passed.
 * Example:
 * ```kotlin
 * object Document: WithoutArgsScreen() {
 *    override val route = AppDecorator.DOCUMENT
 *    override val arguments = emptyList<NamedNavArgument>()
 *    override fun objectParser(entry: NavBackStackEntry) {}
 * }
 *    ```
 */
abstract class WithoutArgsScreen : NodeScreen, NavDestination<Unit> {
    override val arguments: List<NamedNavArgument> get() = emptyList()

    override fun objectParser(entry: NavBackStackEntry) {}
    override fun destination(arg: Unit): DestinationRoute = route

}