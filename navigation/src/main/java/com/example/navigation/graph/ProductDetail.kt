package com.example.navigation.graph

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.navigation.utils.AppArgument
import com.example.navigation.utils.AppDecorator
import com.example.navigation.utils.ArgsScreen
import com.example.navigation.utils.DestinationRoute

/**
 * Represents the product detail screen in the navigation graph.
 * It requires a product ID as an argument to display the details of a specific product.
 */
object ProductDetail: ArgsScreen<String> {
    override val route: String = "${AppDecorator.PRODUCT_DETAIL}/${AppArgument.PRODUCT_ID}"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(AppArgument.PRODUCT_ID) {type = NavType.StringType}
    )

    /**
     * Constructs the destination route for the product detail screen with the given product ID.
     *
     * @param arg The product ID to be included in the route.
     * @return The complete destination route as a string.
     */
    override fun destination(arg: String): DestinationRoute {
        return "$route/$arg"
    }

    /**
     * Parses the product ID from the navigation back stack entry.
     *
     * @param entry The navigation back stack entry containing the arguments.
     * @return The product ID as a string, or an empty string if not found.
     */
    override fun objectParser(entry: NavBackStackEntry): String {
        return entry.arguments?.getString(AppArgument.PRODUCT_ID) ?: ""
    }
}