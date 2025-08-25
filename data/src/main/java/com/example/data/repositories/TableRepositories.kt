package com.example.data.repositories

import com.example.core.models.Table
import com.example.core.models.request.table.GetTableRequest
import com.example.core.models.response.PaginationResponse
import kotlinx.coroutines.flow.Flow

interface TableRepositories {
    fun findAllTableAndReservationByDate(getTableRequest: GetTableRequest): Flow<PaginationResponse<Table>>
}