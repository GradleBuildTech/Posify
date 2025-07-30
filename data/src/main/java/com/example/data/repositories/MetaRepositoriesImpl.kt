package com.example.data.repositories

import com.example.core.models.meta.Tennant
import com.example.core.models.response.ResponseData
import com.example.data.extensions.runCatchingApiCall
import com.example.data.services.MetaService
import com.example.domain.repositories.MetaRepositories
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MetaRepositoriesImpl @Inject constructor(
    private val metaService: MetaService,
) : MetaRepositories{
    override fun getTenantByDomain(domain: String): Flow<ResponseData<Tennant>> = runCatchingApiCall {
        metaService.getTenantByDomain(mapOf("domainUrl" to domain))
    }
}
