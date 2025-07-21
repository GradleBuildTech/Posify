package com.example.components.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object GroundTheme {
    val Orange = Color(0xFFFF6B35)
    val LightOrange = Color(0xFFFF8E53)
    val VeryLightOrange = Color(0xFFFFA366)
    val OrangeBg = Color(0xFFFFF8F5)
}

// This file defines a composable function that builds a gradient background with floating shapes.
// It uses a vertical gradient with specified colors and allows for additional child composables to be rendered on top of the background.
@Composable
fun BuildGradientBackground(
    modifier: Modifier = Modifier,
    child: @Composable () -> Unit = { /* No child by default */ }
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GroundTheme.Orange,
                        GroundTheme.LightOrange,
                        GroundTheme.VeryLightOrange,
                        GroundTheme.OrangeBg
                    )
                )
            ),
    ) {
        // Floating shapes can be added here to enhance the background
        FloatingShapes()
        // Render the child composable if provided
        child()
    }
}


@Composable
fun FloatingShapes() {
    // This is a placeholder for the FloatingShapes implementation.
    // The actual implementation would include UI components and logic for displaying floating shapes.
    // You can use Box or other composables to create the desired shapes and animations.
    val infiniteTransition = rememberInfiniteTransition()

    repeat(3) { index ->
        val animateY = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = -20f, // Stagger the Y position
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 3000 + (index * 1000),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
        Box(
            modifier = Modifier
                .size((80 - index * 20).dp)
                .offset(
                    x = (50 + index * 100).dp,
                    y = (100 + index * 50 + animateY.value).dp
                )
                .alpha(0.1f)
                .background(Color.White, CircleShape)
        )

    }
}