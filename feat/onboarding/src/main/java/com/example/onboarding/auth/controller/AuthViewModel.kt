package com.example.onboarding.auth.controller

import com.example.core.internal.machine.ViewModelMachine
import com.example.core.models.User
import com.example.core.models.meta.OrgAccess
import com.example.core.models.meta.PosTerminalAccess
import com.example.core.models.meta.RoleAccess
import com.example.domain.usecase.auth.AuthSaveUser
import com.example.domain.usecase.auth.GetPosTerminalAccess
import com.example.domain.usecase.auth.SaveInformation
import com.example.domain.usecase.auth.SignInUseCase
import com.example.manager.auth.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val authSaveUser: AuthSaveUser,
    private val saveInformation: SaveInformation,
    private val getPosTerminalAccess: GetPosTerminalAccess
) : ViewModelMachine<AuthState, AuthEvent>(
    initialState = AuthState()
) {

    override suspend fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignIn -> {
                handleSignIn(event.username, event.password, event.domainUrl)
            }
            AuthEvent.NavigateToMain -> {}
            is AuthEvent.SelectOrg -> {
                selectOrg(event.org)
            }
            is AuthEvent.SelectRole -> {
               selectRole(event.role)
            }
            is AuthEvent.GetPosTerminalAccess -> {}
            is AuthEvent.SelectPosTerminal -> {
                selectPosTerminal(event.posTerminal)
            }
            is AuthEvent.SaveInformation -> {
                saveInformation()
            }
        }
    }


    /**
     * Handles the sign-in process by collecting the result from the SignInUseCase.
     * It updates the UI state based on the success or failure of the sign-in attempt.
     * @param username The username or email of the user.
     * @param password The password of the user.
     * @param domainUrl The domain URL for the authentication request.
     */
    private suspend fun handleSignIn(username: String, password: String, domainUrl: String) {
        setUiState { copy(uiState = AuthStateUiState.LOADING) }
        signInUseCase.invoke(
            email = username,
            password = password,
            domainUrl = domainUrl
        ).collect { either ->
            if(either.isLeft()) {
                return@collect setUiState {
                    copy(
                        uiState = AuthStateUiState.ERROR,
                        errorMessage = either.leftValue()?.errorMessage
                    )
                }
            }
            either.rightValue()?.let { user ->
                authSaveUser.invoke(
                    user.copy(tenant = user.tenant?.copy(name = domainUrl))
                )
                val listOrg = user.orgAccess
                val listRole = user.roleAccess
                val listPosTerminalAccess = user.posTerminalAccess
                if(listRole.size == 1 && listOrg.size == 1 && listPosTerminalAccess.size == 1) {
                    val orgSelected = listOrg.firstOrNull()
                    val roleSelected = listRole.firstOrNull()
                    val posTerminalSelected = listPosTerminalAccess.firstOrNull()
                    saveInformation(user = user, orgSelected, roleSelected, posTerminalSelected)
                }
                setUiState {
                    copy(
                        userLogin = user,
                        showSelectRoleForm = true,
                        uiState = AuthStateUiState.SUCCESS,
                        listOrg = listOrg,
                        listRole = listRole,
                        orgSelected = listOrg.firstOrNull(),
                        roleSelected = listRole.firstOrNull(),
                    )
                }
            }
        }
    }

    private fun selectOrg(orgAccess: OrgAccess) {
        setUiState {
            copy(
                orgSelected = orgAccess,
                posTerminalSelected = null
            )
        }
    }

    private fun selectPosTerminal(posTerminalAccess: PosTerminalAccess) {
        setUiState {
            copy(
                posTerminalSelected = posTerminalAccess,
                uiState = AuthStateUiState.SUCCESS
            )
        }
    }


    suspend fun selectRole(roleAccess: RoleAccess) {
        setUiState {
            copy(
                roleSelected = roleAccess,
                posTerminalSelected = null,
                uiState = AuthStateUiState.LOADING
            )
        }
        val orgId = uiState.value.orgSelected?.orgId
        val userId = uiState.value.userLogin?.id
        if(orgId == null || userId == null) { return }
        getPosTerminalAccess.invoke(
            userId = userId,
            orgId = orgId
        ).collect { either ->
            if(either.isLeft()) {
                return@collect setUiState {
                    copy(
                        uiState = AuthStateUiState.ERROR,
                        errorMessage = either.leftValue()?.errorMessage
                    )
                }
            }

            val posTerminalAccessList = either.rightValue() ?: emptyList()

            setUiState {
                copy(
                    listPosTerminalAccess = posTerminalAccessList,
                    posTerminalSelected = posTerminalAccessList.firstOrNull(),
                    uiState = AuthStateUiState.SUCCESS
                )
            }
        }

    }

    /**
     * Saves the user information and updates the authentication state.
     * This function is called after a successful sign-in or when the user selects an organization, role, or POS terminal.
     */
    private suspend fun saveInformation(
        user: User? = null,
        orgSelected: OrgAccess? = null,
        roleSelected: RoleAccess? = null,
        posTerminalSelected: PosTerminalAccess? = null,
    ) {
        val authState =  uiState.value
        val stateUser = user ?: authState.userLogin
        if(stateUser == null) {
            setUiState {
                copy(
                    uiState = AuthStateUiState.ERROR,
                    errorMessage = "User information is missing."
                )
            }
            return
        }
        saveInformation.invoke(
            user = stateUser,
            orgAccess = orgSelected ?: authState.orgSelected,
            roleAccess = roleSelected ?: authState.roleSelected,
            posTerminalAccess = posTerminalSelected ?: authState.posTerminalSelected
        )
        AuthManager.authenticate(stateUser.id?.toString() ?: "")
    }
}