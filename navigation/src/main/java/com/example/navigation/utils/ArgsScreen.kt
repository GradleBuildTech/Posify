package com.example.navigation.utils


/**
 * Represents a screen that requires arguments to navigate to it.
 * This interface extends [NodeScreen] and [NavDestination] to provide
 * a way to define a navigation destination that requires an argument of type [T].
 * @param T The type of the argument required by the destination.
 * * Example:
 * * ```kotlin
 * * object SignIn : ArgsScreen<SignInArgs> {
 * *     override val route = "signIn"
 * *     override val arguments = SignInArgs.navArguments
 * *     override fun objectParser(entry: NavBackStackEntry): SignInArgs {
 * *         return SignInArgs.from(entry)
 * *     }
 * *     override fun destination(arg: SignInArgs): DestinationRoute {
 * *         return "$route/${arg.username}/${arg.password}"
 * *     }
 * * }
 */
interface ArgsScreen<T>: NodeScreen, NavDestination<T>