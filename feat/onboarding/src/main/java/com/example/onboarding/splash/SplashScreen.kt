package com.example.onboarding.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.components.common.AnimationType
import com.example.components.common.BuildAppLoading
import com.example.core.lib.constants.DrawbleConst

@Composable
fun SplashScreen() {
    Scaffold {
        contentPadding ->
        // This is a placeholder for the SplashScreen implementation.
        Box(
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            BuildAppLoading(
                modifier = Modifier.padding(contentPadding),
                animationType = AnimationType.SCALE,
                sizeDp = 120.dp,
                imageRes = DrawbleConst.APP_ICON // replace with your actual drawable resource
            )
        }
    }
}