package com.example.data.services

import com.example.core.models.Table
import com.example.core.models.response.PaginationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface TableService {
    companion object{
        const val BRANCH = "/app/api/v1/tables"
        const val FIND_ALL_TABLE_RESERVATIONS = "$BRANCH/findAllTableAndReservationByDate"
    }


    /**
     * Fetches a paginated list of tables along with their reservations based on the provided query parameters.
     *
     * @param queryMap A map containing query parameters such as date, page number, and page size.
     * @return A Response object containing a PaginationResponse with a list of Table objects.
     */
    @GET(FIND_ALL_TABLE_RESERVATIONS)
    suspend fun findAllTableAndReservationByDate(
        @QueryMap queryMap: Map<String, String>
    ): Response<PaginationResponse<Table>>
}