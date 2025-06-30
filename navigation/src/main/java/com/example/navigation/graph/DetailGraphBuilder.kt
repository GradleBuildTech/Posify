package com.example.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

/**
 * Defines the detail graph for the application.
 * This graph includes screens for authentication, order details, product details,....
 * The screens are provided as composable functions that can be invoked to display the respective content.
 */
data class DetailScreen(
    val auth: @Composable () -> Unit,
    val order: @Composable () -> Unit,
    val productDetail: @Composable (String) -> Unit
)

/**
 * [NavGraphBuilder.detailGraph] is used to build the detail navigation graph.
 * It defines the structure of the graph, including the start destination and the composable screens.
 * This graph includes:
 * - Authentication screen
 * - Order screen
 * - Product detail screen,....
 * The product detail screen takes a product ID as an argument.
 * This allows for dynamic navigation to product details based on the selected product.
 * Example usage:
 *
 */
fun NavGraphBuilder.detailGraph(screens: DetailScreen) {
    navigation(
        startDestination = DetailGraph.startDestination,
        route = DetailGraph.route
    ) {
        composable(DetailGraph.auth.route) {
            screens.auth()
        }

        composable(DetailGraph.order.route) {
            screens.order()
        }

        composable(DetailGraph.productDetail.route, arguments = DetailGraph.productDetail.arguments) {
            val productId = DetailGraph.productDetail.objectParser(it)
            screens.productDetail(productId)
        }
    }
}