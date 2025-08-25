package com.example.data.services

import com.example.core.models.Floor
import com.example.core.models.response.PaginationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FloorService {
    companion object {
        const val FLOOR_BRANCH = "/app/api/v1/floors"
        const val FIND_ALL = "$FLOOR_BRANCH/findAll"
    }

    /**
     * Fetches a paginated list of floors based on the provided query parameters.
     */
    @GET(FIND_ALL)
    suspend fun findAllFloors(
        @QueryMap queryMap: Map<String, String>
    ): Response<PaginationResponse<Floor>>
}