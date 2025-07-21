package com.example.onboarding.auth

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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.components.common.BuildButton
import com.example.components.common.BuildGradientBackground
import com.example.components.common.BuildHeroSection
import com.example.components.common.textField.BuildTextField
import com.example.core.lib.constants.DesignSystem
import com.example.core.lib.constants.DisplayMetric
import com.example.core.lib.constants.LayoutConstants
import com.example.onboarding.auth.controller.AuthViewModel


@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    authController: AuthViewModel = hiltViewModel()
) {
    Scaffold { contentPadding ->
        // This is a placeholder for the AuthScreen implementation.
        // The actual implementation would include UI components and logic for user authentication.
        BuildGradientBackground(
            modifier = Modifier.padding(contentPadding),
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
                        LoginForm(
                            onLogin = { username, password ->
                                // Handle login logic here
                            },
                            onForgotPassword = {
                                // Handle forgot password logic here
                            },
                            onSignUp = {
                                // Handle sign up logic here
                            }
                        )
                    }
                    Footer()
                }
            }
        )
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
    onLogin: (String, String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    var username = ""
    var password = ""
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
                text = "Đăng nhập",
                textAlign = TextAlign.Center,
                style = DesignSystem.TITLE_LARGE_STYLE.copy(
                    color = primaryColor,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(LayoutConstants.TRIPLE_SPACING.dp))
            BuildTextField(
                hint = "Tên đăng nhập",
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
                hint = "Mật khẩu",
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
                        text = "Lưu tài khoản",
                        style = DesignSystem.TITLE_SMALL_STYLE
                    )
                }

                Text(
                    text = "Quên mật khẩu?",
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
                onPress = { onLogin(username, password) },

            ) {
                Text(
                    text = "Đăng nhập",
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
                    text = " Hoặc đăng nhập bằng ",
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
                    text = "Đăng ký dùng thử",
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
