package com.example.offline.repository.database.converter

import com.example.offline.repository.domain.org.OrgEntity


fun OrgEntity.combineWith(
    other: OrgEntity
): OrgEntity? {
    if (this.cid != other.cid) {
        return null
    }
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
        routeFunction = this.routeFunction
    ).apply {
        cid = "%s:%s".format(orgId, tenantId)
    }
}
