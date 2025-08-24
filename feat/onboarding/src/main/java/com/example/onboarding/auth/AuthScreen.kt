package com.example.onboarding.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.components.common.*
import com.example.components.common.textField.BuildTextField
import com.example.core.R
import com.example.core.lib.constants.DesignSystem
import com.example.core.lib.constants.DisplayMetric
import com.example.core.lib.constants.LayoutConstants
import com.example.core.models.meta.OrgAccess
import com.example.core.models.meta.PosTerminalAccess
import com.example.core.models.meta.RoleAccess
import com.example.onboarding.auth.controller.*

@Composable
fun AuthScreen(
    authController: AuthViewModel = hiltViewModel(),
    openSignUp: () -> Unit,
) {
    val uiState by authController.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.uiState) {
        if (uiState.uiState == AuthStateUiState.ERROR) {
//            context.showToast(R.string.auth_error)
        }
    }

    AuthContent(
        modifier = Modifier.fillMaxSize(),
        onLogin = { username, password, domainUrl ->
            authController.onEvent(AuthEvent.SignIn(username, password, domainUrl))
        },
        onForgotPassword = {
//            Toast.makeText(context, R.string.forgot_password_clicked, Toast.LENGTH_SHORT).show()
        },
        onSignUp = openSignUp,
        isSignInLoading = uiState.uiState.isLoading(),
        showSelectRoleForm = uiState.showSelectRoleForm,
        listOrgAccess = uiState.listOrg,
        listRoleAccess = uiState.listRole,
        listPosTerminalAccess = uiState.listPosTerminalAccess,
        orgSelected = uiState.orgSelected,
        posTerminalSelected = uiState.posTerminalSelected,
        roleSelected = uiState.roleSelected,
        onSelectRole = { authController.onEvent(AuthEvent.SelectRole(it)) },
        onSelectOrg = { authController.onEvent(AuthEvent.SelectOrg(it)) },
        onSelectPosTerminal = { authController.onEvent(AuthEvent.SelectPosTerminal(it)) },
        onSave = { authController.onEvent(AuthEvent.SaveInformation) }
    )
}

@Composable
private fun AuthContent(
    modifier: Modifier,
    onLogin: (String, String, String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit,
    isSignInLoading: Boolean,
    showSelectRoleForm: Boolean,
    listOrgAccess: List<OrgAccess>,
    listRoleAccess: List<RoleAccess>,
    listPosTerminalAccess: List<PosTerminalAccess>,
    orgSelected: OrgAccess?,
    roleSelected: RoleAccess?,
    posTerminalSelected: PosTerminalAccess?,
    onSelectRole: (RoleAccess) -> Unit,
    onSelectOrg: (OrgAccess) -> Unit,
    onSelectPosTerminal: (PosTerminalAccess) -> Unit,
    onSave: () -> Unit,
) {
    Scaffold { paddingValues ->
        BuildGradientBackground(
            modifier = modifier.padding(paddingValues),
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

                        Spacer(Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))

                        if (showSelectRoleForm) {
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
private fun SelectRoleForm(
    listOrgAccess: List<OrgAccess>,
    listRoleAccess: List<RoleAccess>,
    listPosTerminalAccess: List<PosTerminalAccess>,
    orgSelected: OrgAccess?,
    posTerminalSelected: PosTerminalAccess?,
    roleSelected: RoleAccess?,
    onSelectRole: (RoleAccess) -> Unit,
    onSelectOrg: (OrgAccess) -> Unit,
    onSelectPosTerminal: (PosTerminalAccess) -> Unit,
    onSave: () -> Unit
) {
    val dropdownModifier = Modifier
        .padding(horizontal = LayoutConstants.PADDING_SMALL.dp)
        .fillMaxWidth()
        .height(230.dp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LayoutConstants.PADDING_SMALL.dp),
        shape = RoundedCornerShape(DisplayMetric.CARD_BORDER_RADIUS.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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

            Spacer(Modifier.height(LayoutConstants.TRIPLE_SPACING.dp))

            BuildAppDropdownField(
                modifier = Modifier.fillMaxWidth(),
                dropdownModifier = dropdownModifier,
                hint = stringResource(R.string.selectOrganization),
                items = listOrgAccess,
                onValueChange = onSelectOrg,
                prefixIcon = Icons.Default.Home,
                itemLabel = { it.orgName.orEmpty() },
                value = orgSelected,
            )

            Spacer(Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))

            BuildAppDropdownField(
                modifier = Modifier.fillMaxWidth(),
                dropdownModifier = dropdownModifier,
                hint = stringResource(R.string.selectRole),
                items = listRoleAccess,
                onValueChange = onSelectRole,
                prefixIcon = Icons.Default.Person,
                itemLabel = { it.name.orEmpty() },
                value = roleSelected,
            )

            Spacer(Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))

            BuildAppDropdownField(
                modifier = Modifier.fillMaxWidth(),
                dropdownModifier = dropdownModifier,
                hint = stringResource(R.string.selectPosTerminal),
                items = listPosTerminalAccess,
                onValueChange = onSelectPosTerminal,
                prefixIcon = Icons.Default.ShoppingCart,
                itemLabel = { it.name.orEmpty() },
                value = posTerminalSelected,
            )

            Spacer(Modifier.height(LayoutConstants.TRIPLE_SPACING.dp))

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

@Composable
private fun LoginForm(
    onLogin: (String, String, String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit,
    isSignInLoading: Boolean
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val fullWidthModifier = Modifier.fillMaxWidth()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var domain by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LayoutConstants.PADDING_SMALL.dp),
        shape = RoundedCornerShape(DisplayMetric.CARD_BORDER_RADIUS.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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

            Spacer(Modifier.height(LayoutConstants.TRIPLE_SPACING.dp))

            BuildTextField(
                hint = stringResource(R.string.restaurantName),
                showCancelIcon = false,
                onSubmit = { domain = it },
                onValueChangedExternally = { domain = it },
                modifier = fullWidthModifier,
                focusColor = primaryColor,
                leadingIcon = Icons.Default.Home,
                textStyle = DesignSystem.TITLE_SMALL_STYLE,
                radius = DisplayMetric.CARD_BORDER_RADIUS.dp
            )

            Spacer(Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))

            BuildTextField(
                hint = stringResource(R.string.userName),
                showCancelIcon = false,
                onSubmit = { username = it },
                onValueChangedExternally = { username = it },
                modifier = fullWidthModifier,
                focusColor = primaryColor,
                leadingIcon = Icons.Default.Person,
                textStyle = DesignSystem.TITLE_SMALL_STYLE,
                radius = DisplayMetric.CARD_BORDER_RADIUS.dp
            )

            Spacer(Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))

            BuildTextField(
                hint = stringResource(R.string.password),
                showCancelIcon = false,
                onSubmit = { password = it },
                onValueChangedExternally = { password = it },
                modifier = fullWidthModifier,
                focusColor = primaryColor,
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
                textStyle = DesignSystem.TITLE_SMALL_STYLE,
                radius = DisplayMetric.CARD_BORDER_RADIUS.dp
            )

            Spacer(Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(checkedColor = primaryColor)
                    )
                    Text(
                        text = stringResource(R.string.saveAccount),
                        style = DesignSystem.TITLE_SMALL_STYLE
                    )
                }

                Text(
                    text = stringResource(R.string.forgotPassword),
                    style = DesignSystem.TITLE_SMALL_STYLE.copy(color = primaryColor),
                    modifier = Modifier.clickable(onClick = onForgotPassword)
                )
            }

            Spacer(Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))

            BuildButton(
                modifier = Modifier.fillMaxWidth(),
                color = primaryColor,
                height = 50.dp,
                loading = isSignInLoading,
                onPress = { onLogin(username, password, domain) }
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

            Spacer(Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                Text(
                    text = stringResource(R.string.orSignInWith),
                    style = DesignSystem.TITLE_SMALL_STYLE,
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
            }

            Spacer(Modifier.height(LayoutConstants.DOUBLE_SPACING.dp))

            BuildButton(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent,
                borderColor = primaryColor,
                height = 50.dp,
                onPress = onSignUp
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
