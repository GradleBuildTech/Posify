package com.example.data.services

import com.example.core.models.meta.Tennant
import com.example.core.models.response.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MetaService {
    companion object {
        //Tenant
        const val TENANT_BRANCH = "/app/api/v1/tenant"
        const val GET_BY_DOMAIN = "$TENANT_BRANCH/getbyDomain"
    }

    /**
     * Get tenant by domain
     *
     * @param queryMap Map containing query parameters, e.g., "domainUrl" to specify the domain.
     * @return Response containing the tenant data wrapped in ResponseData.
     */
    @GET(GET_BY_DOMAIN)
    suspend fun getTenantByDomain(
        @QueryMap queryMap: Map<String, String>
    ): Response<ResponseData<Tennant>>

}