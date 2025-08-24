package com.example.order.viewTable.controller

import com.example.client.network.NetworkMonitor
import com.example.core.internal.machine.ViewModelMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewTableVIewModel @Inject constructor(
    networkMonitor: NetworkMonitor
): ViewModelMachine<OrderViewTableStateData, OrderViewTableEvent>(
    initialState = OrderViewTableStateData()
) {
    val networkStatus = networkMonitor.networkStatus

    override suspend fun handleEvent(event: OrderViewTableEvent) {

    }

}