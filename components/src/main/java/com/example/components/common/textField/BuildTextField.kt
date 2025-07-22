package com.example.components.common.textField

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.internal.machine.StateMachine
import kotlinx.coroutines.launch

/**
 * BuildTextField is a composable function that creates a customizable text field.
 * It supports features like password visibility toggle, cancel icon, and custom keyboard actions.
 *
 * @param modifier Modifier to be applied to the TextField.
 * @param hint Optional hint text displayed when the TextField is empty.
 * @param onSubmit Callback invoked when the user submits the text (e.g., pressing "Done").
 * @param onCancel Callback invoked when the cancel icon is clicked.
 * @param isPassword Boolean indicating if the TextField is for password input.
 * @param showCancelIcon Boolean indicating if the cancel icon should be shown.
 * @param showEyeIcon Boolean indicating if the eye icon for password visibility should be shown.
 * @param textStyle Custom text style for the TextField.
 * @param keyboardOptions Keyboard options for the TextField.
 * @param enabled Boolean indicating if the TextField is enabled.
 * @param readOnly Boolean indicating if the TextField is read-only.
 * @param singleLine Boolean indicating if the TextField should be single-line.
 * @param maxLines Maximum number of lines for the TextField.
 * @param visualTransformation Visual transformation applied to the text input.
 */
@Composable
fun BuildTextField(
    modifier: Modifier = Modifier,
    hint: String? = null,
    onSubmit: ((String) -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
    isPassword: Boolean = false,
    showCancelIcon: Boolean = false,
    showEyeIcon: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    radius: Dp = 8.dp,
    focusColor: Color? = null, // optional focus color
    leadingIcon: ImageVector? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChangedExternally: ((String) -> Unit)? = null // optional callback
) {
    val coroutineScope = rememberCoroutineScope()

    val stateMachine = remember {
        StateMachine(
            initialState = Idle(),
            stateFunction = textFieldStateFunction,
            defaultEventHandler = { state, _ -> state }
        )
    }

    val color = focusColor ?: MaterialTheme.colorScheme.primary
    val state = stateMachine.stateFlow.collectAsState()
    val value = state.value.text

    var isPasswordVisible = remember { mutableStateOf(false) }

    val transformed = when {
        isPassword && !isPasswordVisible.value -> PasswordVisualTransformation()
        else -> visualTransformation
    }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            coroutineScope.launch {
                stateMachine.sendEvent(InputText(it))
                onValueChangedExternally?.invoke(it)
            }
        },
        textStyle = textStyle,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = maxLines,
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = color
                )
            }
        },
        placeholder = {
            if (!hint.isNullOrEmpty()) Text(hint, style = textStyle.copy(color = Color.Gray))
        },
        visualTransformation = transformed,
        keyboardOptions = keyboardOptions.copy(
            imeAction = if (onSubmit != null) ImeAction.Done else keyboardOptions.imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onSubmit?.invoke(value)
            }
        ),
        trailingIcon = {
            when {
                showCancelIcon && value.isNotEmpty() -> {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            stateMachine.sendEvent(ClearText)
                            onCancel?.invoke()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear text"
                        )
                    }
                }

                showEyeIcon && isPassword -> {
                    IconButton(onClick = { isPasswordVisible.value = !isPasswordVisible.value }) {
                        Icon(
                            imageVector = if (isPasswordVisible.value) Icons.Filled.Done else Icons.Default.Done,
                            contentDescription = if (isPasswordVisible.value) "Hide password" else "Show password"
                        )
                    }
                }
            }
        },
        shape = RoundedCornerShape(radius),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = color,
            unfocusedBorderColor = color,
        ),
    )
}



@Preview
@Composable
fun BuildTextFieldPreview() {
    BuildTextField(
        hint = "Enter text",
        onSubmit = { text -> println("Submitted: $text") },
        onCancel = { println("Cancelled") },
        isPassword = false,
        showCancelIcon = true,
        showEyeIcon = false,
        textStyle = TextStyle(color = Color.Black)
    )
}