package com.example.domain.usecase.auth

import com.example.client.security.SecureTokenLocalService
import com.example.core.models.User
import com.example.core.models.meta.OrgAccess
import com.example.core.models.meta.PosTerminalAccess
import com.example.core.models.meta.RoleAccess
import com.example.offline.repository.domain.org.DatabaseOrgRepository
import javax.inject.Inject

class SaveInformation @Inject constructor(
    private val orgDataBase: DatabaseOrgRepository,
    private val secureTokenLocalService: SecureTokenLocalService,
){
    /**
     * Saves the organization information to the database.
     *
     * @param orgId The ID of the organization to save.
     * @param orgName The name of the organization to save.
     */
    suspend operator fun invoke(
        user: User,
        orgAccess: OrgAccess? = null,
        roleAccess: RoleAccess? = null,
        posTerminalAccess: PosTerminalAccess? = null
    ) {
        val tenantId = orgAccess?.tenantId
        if(tenantId == null) return
        val currentOrg = orgDataBase.getOrg()
        orgDataBase.insertOrg(currentOrg.copy(
            orgId = orgAccess.orgId,
            name = orgAccess.orgName ?: "",
            tenantName = user.tenant?.name ?: "",
            tenantId = tenantId.toString(),
            roleId = roleAccess?.roleId,
            roleCode = roleAccess?.code,
            posTerminalId = posTerminalAccess?.posTerminalId
        ))
        val jwtToken = user.jwtToken
        val refreshToken = user.refreshToken
        if (jwtToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) return
        secureTokenLocalService.saveToken(jwtToken)
        secureTokenLocalService.saveRefreshToken(refreshToken)

    }
}