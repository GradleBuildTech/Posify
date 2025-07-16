package com.example.onboarding.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.components.common.textField.BuildTextField


@Composable
fun AuthScreen() {
    Scaffold { contentPadding ->
        // This is a placeholder for the AuthScreen implementation.
        // The actual implementation would include UI components and logic for user authentication.
        Column(
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
    }
}