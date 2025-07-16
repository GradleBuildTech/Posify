package com.example.onboarding.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.components.common.AnimationType
import com.example.components.common.BuildAppLoading
import com.example.components.common.BuildAppName
import com.example.core.lib.constants.DesignSystem
import com.example.core.lib.constants.DrawableConst
import com.example.core.lib.constants.LayoutConstants

@Composable
fun SplashScreen(
    splashController: SplashController = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        // This is where you can perform any initialization or checks needed for the splash screen.
        // For example, you might want to check if the user is authenticated or load initial data.
        splashController.checkToken()
    }

    Scaffold {
        contentPadding ->
        // This is a placeholder for the SplashScreen implementation.
        Column (
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BuildAppLoading(
                modifier = Modifier.padding(contentPadding),
                animationType = AnimationType.SCALE,
                sizeDp = 120.dp,
                imageRes = DrawableConst.APP_ICON // replace with your actual drawable resource
            )
            Spacer(modifier = Modifier.height(LayoutConstants.SPACING.dp))
            BuildAppName(
                textStyle = DesignSystem.TITLE_LARGE_STYLE
            )
        }
    }
}