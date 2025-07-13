package com.example.posNativeApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.navigation.core.AppNavigation
import com.example.navigation.core.Navigator
import com.example.posNativeApp.ui.theme.PosNativeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PosNativeAppTheme {
                AppNavigation(navigator = navigator)
            }
        }
    }
}

