package com.example.order.viewTable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.client.network.NetworkStatus
import com.example.order.viewTable.controller.OrderViewTableVIewModel


@Composable
fun OrderViewTableScreen(
    viewModel: OrderViewTableVIewModel = hiltViewModel()
){
    val status = viewModel.networkStatus.collectAsState()

    LaunchedEffect(status.value) {
        when(status.value) {
            NetworkStatus.CONNECTED -> {}
            NetworkStatus.DISCONNECTED -> {}
            NetworkStatus.INITIAL -> {}
        }
    }
}