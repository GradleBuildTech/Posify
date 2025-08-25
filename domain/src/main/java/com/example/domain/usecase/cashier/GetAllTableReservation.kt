package com.example.domain.usecase.cashier

import com.example.core.models.Table
import com.example.core.models.request.table.GetTableRequest
import com.example.core.models.stateData.Either
import com.example.core.models.stateData.ExceptionState
import com.example.core.utils.DateUtils
import com.example.data.extensions.mapAndConverterToStateData
import com.example.data.repositories.TableRepositories
import com.example.offline.repository.domain.org.DatabaseOrgRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllTableReservation @Inject constructor(
    private val tableRepositories: TableRepositories,
    private val orgDataBaseLocal: DatabaseOrgRepository
) {
    operator fun invoke(): Flow<Either<ExceptionState, List<Table>>> = flow {
        val response = tableRepositories.findAllTableAndReservationByDate(
            GetTableRequest(
                page = 0,
                pageSize = 999,
                reservationDate = DateUtils().getCurrentDate(),
                orgId = orgDataBaseLocal.getOrg().tenantId.toIntOrNull() ?: 0,
            )
        ).first().mapAndConverterToStateData()
        if(response.isLeft()) {
            return@flow emit(Either.Left(response.leftValue()!!))
        }
        val tableList = response.rightValue() ?: emptyList()
        emit(Either.Right(tableList))
    }
}