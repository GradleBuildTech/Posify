package com.example.order.viewTable.controller

import androidx.lifecycle.viewModelScope
import com.example.client.network.NetworkMonitor
import com.example.core.internal.machine.ViewModelMachine
import com.example.core.models.Floor
import com.example.core.models.Table
import com.example.core.models.default
import com.example.core.models.enum.TableStatus
import com.example.core.models.request.table.GetTableRequest
import com.example.core.models.stateData.Either
import com.example.core.models.stateData.ExceptionState
import com.example.core.presentation.WhileSubscribedOrRetained
import com.example.domain.usecase.cashier.GetAllFloors
import com.example.domain.usecase.cashier.GetAllTableReservation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewTableVIewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val getAllTableReservation: GetAllTableReservation,
    findAllFloors: GetAllFloors
): ViewModelMachine<OrderViewTableStateData, OrderViewTableEvent>(
    initialState = OrderViewTableStateData()
) {
    val networkStatus = networkMonitor.networkStatus

    /**
     * All table reservation flow
     */
    private val allTableReservation = getAllTableReservation.invoke(GetTableRequest()).stateIn(
        viewModelScope,
        started = WhileSubscribedOrRetained,
        initialValue = Either.Left(ExceptionState)
    )
    private val allFloors = findAllFloors.invoke().stateIn(
        viewModelScope,
        started = WhileSubscribedOrRetained,
        initialValue = Either.Left(ExceptionState)
    )

    private fun filterTable(getTableRequest: GetTableRequest) =
        getAllTableReservation.invoke(getTableRequest).stateIn(
            viewModelScope,
            started = WhileSubscribedOrRetained,
            initialValue = Either.Left(ExceptionState)
        )


    init {
        initialData()
    }


    fun initialData() {
        setUiState {
            copy(
                uiState = OrderViewTableState.LOADING
            )
        }
        viewModelScope.launch {
            combine(
                allTableReservation,
                allFloors
            ) { tableReservation, floors ->
                val floors = if (floors.isRight()) floors.rightValue()!! else emptyList()
                val tableReservation =
                    if (tableReservation.isRight()) tableReservation.rightValue()!! else emptyList()
                setUiState {
                    copy(
                        listFloor = listOf(Floor().default, *floors.toTypedArray()),
                        floorSelected = Floor().default,
                        listTableReservation = tableReservation,
                        uiState = OrderViewTableState.SUCCESS
                    )
                }
            }.collect {
                //Handled in combine
            }
        }
    }

    override suspend fun handleEvent(event: OrderViewTableEvent) {
        when (event) {
            is OrderViewTableEvent.FilterTable -> {
                filterTableByFloor(event.floor, event.tableStatus)
            }

            is OrderViewTableEvent.SelectTable -> {
                selectTable(event.table)
            }
        }
    }

    private fun selectTable(table: Table) {
        setUiState { copy(tableSelected = table) }
    }

    private fun filterTableByFloor(floor: Floor?, status: TableStatus? = null) {
        setUiState {
            copy(
                floorSelected = floor,
                tableStatus = status,
                uiState = OrderViewTableState.LOADING
            )
        }
        val allTables =
            filterTable(GetTableRequest(floorId = floor?.id, isActiveTable = status)).value
        if (allTables.isLeft()) {
            return setUiState {
                copy(
                    listTableReservation = emptyList(),
                    uiState = OrderViewTableState.ERROR,
                )
            }
        }

        val tableList = allTables.rightValue() ?: emptyList()
        setUiState {
            copy(
                listTableReservation = tableList,
                uiState = OrderViewTableState.SUCCESS
            )
        }
    }
}

