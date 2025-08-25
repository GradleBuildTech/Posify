package com.example.domain.usecase.cashier

import com.example.core.models.Floor
import com.example.core.models.enum.YesNoCheck
import com.example.core.models.request.floor.GetFloorRequest
import com.example.core.models.stateData.Either
import com.example.core.models.stateData.ExceptionState
import com.example.data.extensions.mapAndConverterToStateData
import com.example.data.repositories.FloorRepositories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllFloors @Inject constructor(
    private val floorRepositories: FloorRepositories
) {
    operator fun invoke(): Flow<Either<ExceptionState, List<Floor>>> = flow {
        val response = floorRepositories.findAll(
            GetFloorRequest(
                isActive = YesNoCheck.YES
            )
        ).first().mapAndConverterToStateData()
        if(response.isLeft()) {
            return@flow emit(Either.Left(response.leftValue()!!))
        }
        val floorList = response.rightValue() ?: emptyList()
        emit(Either.Right(floorList))
    }
}