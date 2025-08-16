package com.example.onboarding.auth.controller

import com.example.core.models.User
import com.example.core.models.meta.OrgAccess
import com.example.core.models.meta.PosTerminalAccess
import com.example.core.models.meta.RoleAccess

enum class AuthStateUiState {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR,
}

fun AuthStateUiState.isLoading(): Boolean {
    return this == AuthStateUiState.LOADING
}


data class AuthState(
    val uiState: AuthStateUiState = AuthStateUiState.IDLE,
    val errorMessage: String? = null,
    val showSelectRoleForm: Boolean = false,

    ///[Ui Data]
    val listOrg: List<OrgAccess> = emptyList(),
    val listRole: List<RoleAccess> = emptyList(),
    val listPosTerminalAccess: List<PosTerminalAccess> = emptyList(),

    ///[Logic data]
    val userLogin: User? = null,
    val orgSelected: OrgAccess? = null,
    val roleSelected: RoleAccess? = null,
    val posTerminalSelected: PosTerminalAccess? = null
)