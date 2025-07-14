package com.example.navigation.graph

import com.example.navigation.utils.NavigationGraph

object DetailGraph: NavigationGraph {
    override val route: String
        get() = ""
    override val startDestination: String
        get() = splash.destination(Unit)

    val auth = Auth
    val order = Order
    val productDetail = ProductDetail
    val splash = Splash
}