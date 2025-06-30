package com.example.navigation.graph

import com.example.navigation.utils.AppDecorator
import com.example.navigation.utils.WithoutArgsScreen

// This file defines the Order screen in the navigation graph.
object Auth: WithoutArgsScreen() {
    override val route = AppDecorator.AUTH
}