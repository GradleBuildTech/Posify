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

/*
    * Represents an organization with various attributes such as tenant ID, name, and optional IDs for organization, POS terminal, role, etc.
    * This class can be used to manage organizational data within the application.
    * The `Empty` object provides a default instance of `Org` with all fields initialized to null or empty strings.
    * This is useful for initializing variables or passing default values when no specific organization data is available.
    * Example usage:
    * val org = Org.Empty
 */
val Org.empty: Org
    get() = Org(
        tenantId = "",
        name = "",
        orgId = null,
        posTerminalId = null,
        roleId = null,
        deviceToken = null,
        userNameGetToken = null,
        passwordGetToken = null,
        userId = null,
        tenantName = null,
        roleCode = null,
        orgName = null,
        routeFunction = null
    )