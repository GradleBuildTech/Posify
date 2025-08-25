package com.example.order.viewTable.controller

import com.example.core.models.Floor
import com.example.core.models.Table
import com.example.core.models.enum.TableStatus

sealed class OrderViewTableEvent {
    data class FilterTable(
        val floor: Floor? = null,
        val tableStatus: TableStatus? = null
    ): OrderViewTableEvent()

    data class SelectTable(val table: Table): OrderViewTableEvent()
}