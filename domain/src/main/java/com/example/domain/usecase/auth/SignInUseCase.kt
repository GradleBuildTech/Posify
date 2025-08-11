package com.example.domain.usecase.auth

import com.example.client.BuildConfig
import com.example.client.security.SecureTokenLocalService
import com.example.core.di.IODispatcher
import com.example.core.models.Org
import com.example.core.models.User
import com.example.core.models.request.auth.AuthRequest
import com.example.core.models.request.auth.SignInRequest
import com.example.core.models.stateData.Either
import com.example.core.models.stateData.ExceptionState
import com.example.data.extensions.mapAndConverterToStateData
import com.example.data.repositories.AuthRepositories
import com.example.data.repositories.MetaRepositories
import com.example.offline.repository.domain.org.DatabaseOrgRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepositories: AuthRepositories,
    private val metaRepositories: MetaRepositories,
    private val orgRepositories: DatabaseOrgRepository,
    private val secureTokenLocalService: SecureTokenLocalService,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(
        email: String,
        password: String,
        domainUrl: String,
    ): Flow<Either<ExceptionState, User>> = flow {
        if(domainUrl.trim().isNotEmpty()) {
            val tenantResponse = metaRepositories
                .getTenantByDomain("${BuildConfig.DOMAIN_URL}${domainUrl.trim()}")
                .first().mapAndConverterToStateData()

            if(tenantResponse.isLeft()) {
               return@flow emit(Either.Left(tenantResponse.leftValue()!!))
            }

            val tenantValue = tenantResponse.rightValue()

            val tenantId = tenantValue?.id?.toString().orEmpty()
            orgRepositories.insertOrg(Org(
                tenantId = tenantId,
                name = tenantValue?.name.orEmpty(),
                tenantName = tenantValue?.name
            ))

            val authRequest = AuthRequest(
                orgId = 1000000,
                tenantId = tenantValue?.id
            )

            val authResponse = authRepositories
                .authenticate(authRequest)
                .first()

            val jwtToken = authResponse.jwtToken

            secureTokenLocalService.cleearTokens()
            secureTokenLocalService.saveToken(jwtToken ?: "")

            val signIn = authRepositories.login(
                SignInRequest(
                    userName = email,
                    password = password,
                )
            ).first().mapAndConverterToStateData()
            return@flow emit(signIn)
        }
        return@flow emit(authRepositories.login(
            SignInRequest(
                userName = email,
                password = password,
            )
        ).first().mapAndConverterToStateData())
    }.flowOn(ioDispatcher)
}
