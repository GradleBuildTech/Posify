package com.example.posNativeApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.navigation.core.AppNavigation
import com.example.navigation.core.Navigator
import com.example.navigation.graph.DetailScreen
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
                AppNavigation(
                    navigator = navigator,
                    detailScreen = DetailScreen(
                        auth = { Greeting(name = "Auth") },
                        order = { Greeting(name = "Home") },
                        productDetail = {it -> Greeting(name = "Product Detail $it") },
                    )
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
