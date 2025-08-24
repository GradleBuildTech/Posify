package com.example.navigation.graph

import com.example.navigation.utils.AppDecorator
import com.example.navigation.utils.WithoutArgsScreen

// This file defines the Order screen in the navigation graph.
object Order: WithoutArgsScreen() {
    override val route = AppDecorator.ORDER
}

object OrderViewTable: WithoutArgsScreen() {
    override val route = AppDecorator.ORDER_VIEW_TABLE
}