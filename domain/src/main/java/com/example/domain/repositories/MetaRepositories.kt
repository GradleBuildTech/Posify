package com.example.domain.repositories

import com.example.core.models.meta.Tennant
import com.example.core.models.response.ResponseData
import kotlinx.coroutines.flow.Flow

interface MetaRepositories {
    fun getTenantByDomain(domain: String): Flow<ResponseData<Tennant>>
}