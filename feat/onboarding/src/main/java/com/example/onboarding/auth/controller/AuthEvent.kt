package com.example.onboarding.auth.controller

import com.example.core.models.meta.OrgAccess
import com.example.core.models.meta.PosTerminalAccess
import com.example.core.models.meta.RoleAccess

sealed class AuthEvent {
    data class SignIn(
        val username: String,
        val password: String,
        val domainUrl: String,
    ) : AuthEvent()

    data object SaveInformation : AuthEvent()
    data class SelectOrg(val org: OrgAccess) : AuthEvent()
    data class SelectRole(val role: RoleAccess) : AuthEvent()
    data class SelectPosTerminal(val posTerminal: PosTerminalAccess) : AuthEvent()
    data object NavigateToMain : AuthEvent()
    data object GetPosTerminalAccess : AuthEvent()
}