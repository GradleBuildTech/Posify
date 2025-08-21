package com.example.onboarding.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.components.common.BuildAppDropdownField
import com.example.components.common.BuildButton
import com.example.components.common.BuildGradientBackground
import com.example.components.common.BuildHeroSection
import com.example.components.common.textField.BuildTextField
import com.example.core.R
import com.example.core.extensions.showToast
import com.example.core.lib.constants.DesignSystem
import com.example.core.lib.constants.DisplayMetric
import com.example.core.lib.constants.LayoutConstants
import com.example.core.models.meta.OrgAccess
import com.example.core.models.meta.PosTerminalAccess
import com.example.core.models.meta.RoleAccess
import com.example.onboarding.auth.controller.AuthEvent
import com.example.onboarding.auth.controller.AuthStateUiState
import com.example.onboarding.auth.controller.AuthViewModel
import com.example.onboarding.auth.controller.isLoading


@Composable
fun AuthScreen(
    authController: AuthViewModel = hiltViewModel(),
    openSignUp: () -> Unit,
) {
    val state = authController.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = state.value) {
        when(state.value.uiState) {
            AuthStateUiState.IDLE -> {}
            AuthStateUiState.LOADING -> {}
            AuthStateUiState.SUCCESS -> {}
            AuthStateUiState.ERROR -> {
                context.showToast(message = "An error occurred during authentication")
            }
        }
    }

    AuthScreen(
        modifier = Modifier.fillMaxSize(),
        onLogin = { username, password, domainUrl ->
            authController.onEvent(AuthEvent.SignIn(
                username = username,
                password = password,
                domainUrl = domainUrl
            ))
        },
        onForgotPassword = {
            // Handle forgot password action
            Toast.makeText(context, "Forgot Password Clicked", Toast.LENGTH_SHORT).show()
        },
        onSignUp = openSignUp,
        isSignInLoading = state.value.uiState.isLoading(),
        showSelectRoleForm = state.value.showSelectRoleForm,
        listOrgAccess = state.value.listOrg,
        listRoleAccess = state.value.listRole,
        listPosTerminalAccess = state.value.listPosTerminalAccess,
        orgSelected = state.value.orgSelected,
        posTerminalSelected = state.value.posTerminalSelected,
        roleSelected = state.value.roleSelected,
        onSelectRole = { role ->
            authController.onEvent(AuthEvent.SelectRole(role))
        },
        onSelectOrg = { org ->
            authController.onEvent(AuthEvent.SelectOrg(org))
        },
        onSelectPosTerminal = { posTerminal ->
            authController.onEvent(AuthEvent.SelectPosTerminal(posTerminal))
        },
        onSave = {
            authController.onEvent(AuthEvent.SaveInformation)
        }
    )


}

@Composable
internal fun AuthScreen(
    modifier: Modifier = Modifier,
    onLogin: (String, String, String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit,
    isSignInLoading: Boolean = false,
    showSelectRoleForm: Boolean = false,
    ///[Choose role Form] - This is used to select the role, organization, and POS terminal
    listOrgAccess: List<OrgAccess>,
    listRoleAccess: List<RoleAccess> = emptyList(),
    listPosTerminalAccess: List<PosTerminalAccess> = emptyList(),
    orgSelected: OrgAccess? = null,
    roleSelected: RoleAccess? = null,
    posTerminalSelected: PosTerminalAccess? = null,
    ///[Choose role Form] - This is used to select the role, organization, and POS terminal
    onSelectRole: (RoleAccess) -> Unit,
    onSelectOrg: (OrgAccess) -> Unit,
    onSelectPosTerminal: (PosTerminalAccess) -> Unit,
    onSave: () -> Unit
) {
    Scaffold { contentPadding ->
        // This is a placeholder for the AuthScreen implementation.
        // The actual implementation would include UI components and logic for user authentication.
        BuildGradientBackground(
            modifier = modifier.padding(contentPadding),
            child = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(LayoutConstants.PADDING_ALL.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BuildHeroSection(
                            icon = Icons.Default.Lock,
                            contentDescription = "Lock Icon",
                            size = 120.dp,
                            iconSize = 60.dp,
                        )
                        Spacer(modifier = Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))
                        if(showSelectRoleForm) {
                            SelectRoleForm(
                                onSave = onSave,
                                listOrgAccess = listOrgAccess,
                                orgSelected = orgSelected,
                                listRoleAccess = listRoleAccess,
                                roleSelected = roleSelected,
                                onSelectRole = onSelectRole,
                                onSelectOrg = onSelectOrg,
                                listPosTerminalAccess = listPosTerminalAccess,
                                posTerminalSelected = posTerminalSelected,
                                onSelectPosTerminal = onSelectPosTerminal,
                            )
                        } else {
                            LoginForm(
                                onLogin = onLogin,
                                onForgotPassword = onForgotPassword,
                                onSignUp = onSignUp,
                                isSignInLoading = isSignInLoading,
                            )
                        }

                    }
                    Footer()
                }
            }
        )
    }
}

@Composable
internal fun SelectRoleForm(
    modifier: Modifier = Modifier,
    listOrgAccess: List<OrgAccess>,
    listRoleAccess: List<RoleAccess>,
    listPosTerminalAccess: List<PosTerminalAccess>,
    orgSelected: OrgAccess? = null,
    posTerminalSelected: PosTerminalAccess? = null,
    roleSelected: RoleAccess? = null,
    onSelectRole: (RoleAccess) -> Unit = {},
    onSelectOrg: (OrgAccess) -> Unit = {},
    onSelectPosTerminal: (PosTerminalAccess) -> Unit = {},

    onSave: () -> Unit = { /* No-op, can be used for further actions */ }
) {
    val dropdownModifier = Modifier
        .padding(horizontal = LayoutConstants.PADDING_SMALL.dp)
        .fillMaxWidth()
        .height(230.dp)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = LayoutConstants.PADDING_SMALL.dp),
        shape = RoundedCornerShape(DisplayMetric.CARD_BORDER_RADIUS.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LayoutConstants.PADDING_ALL.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.selectYourRole),
                textAlign = TextAlign.Center,
                style = DesignSystem.BIG_TITLE_STYLE.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(LayoutConstants.TRIPLE_SPACING.dp))
            BuildAppDropdownField<OrgAccess>(
                modifier = Modifier.fillMaxWidth(),
                dropdownModifier = dropdownModifier,
                hint = stringResource(R.string.selectOrganization),
                items = listOrgAccess,
                onValueChange = {
                    onSelectOrg(it)
                },
                prefixIcon = Icons.Default.Home,
                itemLabel = { it.orgName ?: "" },
                value = orgSelected,
            )
            Spacer(modifier = Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))
            BuildAppDropdownField<RoleAccess>(
                modifier = Modifier.fillMaxWidth(),
                dropdownModifier = dropdownModifier,
                hint = stringResource(R.string.selectRole),
                items = listRoleAccess,
                onValueChange = {
                    onSelectRole(it)
                },
                prefixIcon = Icons.Default.Person,
                itemLabel = { it.name ?: "" },
                value = roleSelected,
            )
            Spacer(modifier = Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))
            BuildAppDropdownField<PosTerminalAccess>(
                modifier = Modifier.fillMaxWidth(),
                dropdownModifier = dropdownModifier,
                hint = stringResource(R.string.selectPosTerminal),
                items = listPosTerminalAccess,
                onValueChange = {
                    onSelectPosTerminal(it)
                },
                prefixIcon = Icons.Default.ShoppingCart,
                itemLabel = { it.name ?: "" },
                value = posTerminalSelected, // No initial value for POS terminal
            )
            Spacer(modifier = Modifier.height(LayoutConstants.TRIPLE_SPACING.dp))

            BuildButton(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                height = 50.dp,
                onPress = onSave
            ) {
                Text(
                    text = stringResource(R.string.continuee),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = DesignSystem.TITLE_MEDIUM_STYLE.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

/**
 * LoginForm is a composable function that displays a login form with fields for username and password,
 * a checkbox for remembering the user, and buttons for login, forgot password, and sign up.
 *
 * @param modifier Modifier to be applied to the LoginForm.
 * @param onLogin Callback invoked when the user submits the login form with username and password.
 * @param onForgotPassword Callback invoked when the user clicks on "Forgot Password".
 * @param onSignUp Callback invoked when the user clicks on "Sign Up".
 */
@Composable
internal fun LoginForm(
    modifier: Modifier = Modifier,
    onLogin: (String, String, String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit,
    isSignInLoading: Boolean
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    var username = ""
    var password = ""
    var resName = ""
    val fullWidthModifier = Modifier.fillMaxWidth()

    var rememberMe = remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = LayoutConstants.PADDING_SMALL.dp),
        shape = RoundedCornerShape(DisplayMetric.CARD_BORDER_RADIUS.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LayoutConstants.PADDING_ALL.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.signIn),
                textAlign = TextAlign.Center,
                style = DesignSystem.BIG_TITLE_STYLE.copy(
                    color = primaryColor,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(LayoutConstants.TRIPLE_SPACING.dp))
            BuildTextField(
                hint = stringResource(R.string.restaurantName),
                showCancelIcon = false,
                onSubmit = { resName = it },
                onValueChangedExternally = { resName = it },
                modifier = fullWidthModifier,
                focusColor = primaryColor,
                leadingIcon = Icons.Default.Home,
                textStyle = DesignSystem.TITLE_SMALL_STYLE.copy(
                    fontWeight = FontWeight.Normal
                ),
                radius = DisplayMetric.CARD_BORDER_RADIUS.dp
            )
            Spacer(modifier = Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))

            BuildTextField(
                hint = stringResource(R.string.userName),
                showCancelIcon = false,
                onSubmit = { username = it },
                onValueChangedExternally = { username = it },
                modifier = fullWidthModifier,
                focusColor = primaryColor,
                leadingIcon = Icons.Default.Person,
                textStyle = DesignSystem.TITLE_SMALL_STYLE.copy(
                    fontWeight = FontWeight.Normal
                ),
                radius = DisplayMetric.CARD_BORDER_RADIUS.dp
            )
            Spacer(modifier = Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))
            BuildTextField(
                hint = stringResource(R.string.password),
                showCancelIcon = false,
                onSubmit = { password = it },
                onValueChangedExternally = { password = it },
                modifier = fullWidthModifier,
                focusColor = primaryColor,
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
                textStyle = DesignSystem.TITLE_SMALL_STYLE.copy(
                    fontWeight = FontWeight.Normal
                ),
                radius = DisplayMetric.CARD_BORDER_RADIUS.dp
            )
            Spacer(modifier = Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberMe.value,
                        onCheckedChange = { },
                        colors = CheckboxDefaults.colors(checkedColor = primaryColor),

                    )
                    Text(
                        text = stringResource(R.string.saveAccount),
                        style = DesignSystem.TITLE_SMALL_STYLE
                    )
                }

                Text(
                    text = stringResource(R.string.forgotPassword),
                    style = DesignSystem.TITLE_SMALL_STYLE.copy(
                        color = primaryColor,
                    ),
                    modifier = Modifier.clickable { /* Handle forgot password */ }
                )
            }
            Spacer(modifier = Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))
            BuildButton(
                modifier = Modifier.fillMaxWidth(),
                color = primaryColor,
                height = 50.dp,
                loading = isSignInLoading,
                onPress = { onLogin(username, password, resName) }
            ) {
                Text(
                    text = stringResource(R.string.signIn),
                    textAlign = TextAlign.Center,
                    modifier = fullWidthModifier,
                    style = DesignSystem.TITLE_MEDIUM_STYLE.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
                Text(
                    text = stringResource(R.string.orSignInWith),
                    style = DesignSystem.TITLE_SMALL_STYLE,
                )
                Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
            }
            Spacer(modifier = Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))

            BuildButton(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent,
                borderColor = primaryColor,
                height = 50.dp,
                onPress = { onSignUp() },
            ) {
                Text(
                    text = stringResource(R.string.signUp),
                    textAlign = TextAlign.Center,
                    modifier = fullWidthModifier,
                    style = DesignSystem.TITLE_MEDIUM_STYLE.copy(
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
internal fun Footer() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Powered by GradleBuildTech | Phiên bản 1.0.0",
            style = DesignSystem.SUBTITLE_SMALL_STYLE.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            ),
        )
    }
}
