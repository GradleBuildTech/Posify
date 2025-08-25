package com.example.data.repositories

import com.example.core.models.Floor
import com.example.core.models.request.floor.GetFloorRequest
import com.example.core.models.response.PaginationResponse
import kotlinx.coroutines.flow.Flow

interface FloorRepositories {
    fun findAll(
        getFloorRequest: GetFloorRequest
    ): Flow<PaginationResponse<Floor>>
}