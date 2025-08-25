package com.example.data.repositories.impl

import com.example.core.models.Floor
import com.example.core.models.request.floor.GetFloorRequest
import com.example.core.models.response.PaginationResponse
import com.example.data.extensions.runCatchingApiCall
import com.example.data.repositories.FloorRepositories
import com.example.data.services.FloorService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FloorRepositoriesImpl @Inject constructor(
    private val floorService: FloorService,
): FloorRepositories {
    override fun findAll(getFloorRequest: GetFloorRequest): Flow<PaginationResponse<Floor>> =
        runCatchingApiCall {
            floorService.findAllFloors(getFloorRequest.toMap())
        }
}