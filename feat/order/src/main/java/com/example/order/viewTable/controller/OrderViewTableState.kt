package com.example.order.viewTable.controller

enum class OrderViewTableState {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR,
}

fun OrderViewTableState.isLoading(): Boolean {
    return this == OrderViewTableState.LOADING
}

data class OrderViewTableStateData(
    val errorMessage: String? = null,
    val uiState: OrderViewTableState = OrderViewTableState.IDLE,
)