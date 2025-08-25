package com.example.order.viewTable.controller

import com.example.core.models.Floor
import com.example.core.models.Table
import com.example.core.models.enum.TableStatus

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
    val listTableReservation: List<Table> = emptyList(),
    val tableSelected: Table? = null,
    val listFloor: List<Floor> = emptyList(),
    val floorSelected: Floor? = null,
    val tableStatus: TableStatus? = TableStatus.ALL
)