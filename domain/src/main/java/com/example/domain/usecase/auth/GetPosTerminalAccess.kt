package com.example.domain.usecase.auth

import com.example.core.di.IODispatcher
import com.example.core.models.meta.PosTerminalAccess
import com.example.core.models.stateData.Either
import com.example.core.models.stateData.ExceptionState
import com.example.data.extensions.mapAndConverterToStateData
import com.example.data.repositories.AuthRepositories
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPosTerminalAccess @Inject constructor(
    private val authRepositories: AuthRepositories,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    /**
     * Retrieves the POS terminal access for a user.
     *
     * @param userId The ID of the user.
     * @param orgId The ID of the organization.
     * @return A flow that emits the list of POS terminal access.
     */
    operator fun invoke(
        userId: Int,
        orgId: Int
    ): Flow<Either<ExceptionState, List<PosTerminalAccess>>> = flow {
       val response = authRepositories.getPosTerminalAccess(userId, orgId)
           .first()
           .mapAndConverterToStateData()
         if (response.isLeft()) {
             return@flow emit(Either.Left(response.leftValue()!!))
         }
        val posTerminalAccessList = response.rightValue() ?: emptyList()
        emit(Either.Right(posTerminalAccessList))

    }
}