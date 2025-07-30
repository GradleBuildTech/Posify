package com.example.core.models

data class Org(
    val tenantId: String,
    val name: String,
    val orgId: Int? = null,
    val posTerminalId: Int? = null,
    val roleId: Int? = null,
    val deviceToken: String? = null,
    val userNameGetToken: String? = null,
    val passwordGetToken: String? = null,
    val userId: Int? = null,
    val tenantName: String? = null,
    val roleCode: String? = null,
    val orgName: String? = null,
    val routeFunction: String? = null
)