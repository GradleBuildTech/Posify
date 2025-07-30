package com.example.offline.repository.domain.org

import com.example.core.models.Org

/**
 * Mapper to convert between OrgEntity and Org model.
 * This mapper is used to convert data between the database entity and the business model.
 */

fun OrgEntity.toModel(): Org {
    return Org(
        orgId = this.orgId,
        tenantId = this.tenantId,
        posTerminalId = this.posTerminalId,
        roleId = this.roleId,
        deviceToken = this.deviceToken,
        userNameGetToken = this.userNameGetToken,
        passwordGetToken = this.passwordGetToken,
        userId = this.userId,
        tenantName = this.tenantName,
        roleCode = this.roleCode,
        orgName = this.orgName,
        routeFunction = this.routeFunction,
        name = this.name

    )
}


fun Org.toEntity(): OrgEntity {
    return OrgEntity(
        orgId = this.orgId,
        tenantId = this.tenantId,
        posTerminalId = this.posTerminalId,
        roleId = this.roleId,
        deviceToken = this.deviceToken,
        userNameGetToken = this.userNameGetToken,
        passwordGetToken = this.passwordGetToken,
        userId = this.userId,
        tenantName = this.tenantName,
        roleCode = this.roleCode,
        orgName = this.orgName,
        routeFunction = this.routeFunction,
        name = this.name
    ).apply {
        cid = "%s:%s".format(orgId, tenantId)
    }
}