package com.example.data.repositories.impl

import com.example.core.models.Table
import com.example.core.models.request.table.GetTableRequest
import com.example.core.models.response.PaginationResponse
import com.example.data.extensions.runCatchingApiCall
import com.example.data.repositories.TableRepositories
import com.example.data.services.TableService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TableRepositoriesImpl @Inject constructor(
    private val tableService: TableService,
) : TableRepositories {


    override fun findAllTableAndReservationByDate(
        getTableRequest: GetTableRequest
    ): Flow<PaginationResponse<Table>> = runCatchingApiCall {
        tableService.findAllTableAndReservationByDate(getTableRequest.toMap())
    }
}