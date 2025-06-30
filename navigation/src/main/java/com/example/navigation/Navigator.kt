package com.example.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Singleton

/**
 * Navigator is a class that provides navigation functionality.
 * It allows navigating to a destination and going back.
 * [navigateTo] is a function that navigates to a destination.
 * [goBack] is a function that goes back.
 * This class is a singleton and is used to manage navigation actions in the application.
 */
@Singleton
class Navigator : NavigationService {
    private val _actions = MutableSharedFlow<NavigationActions>(
        replay = 0,
        extraBufferCapacity = 10
    )

    val actions = _actions.asSharedFlow()

    override fun navigateTo(
        destination: String,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        _actions.tryEmit(NavigationActions.Navigate(destination, navOptions))
    }

    override fun goBack() {
        _actions.tryEmit(NavigationActions.Back)
    }

    sealed class NavigationActions {
        data class Navigate(
            val destination: String,
            val navOptions: NavOptionsBuilder.() -> Unit
        ) : NavigationActions()

        data object Back : NavigationActions()
    }

}