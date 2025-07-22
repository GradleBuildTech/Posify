package com.example.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

/**
 * BuildHeroSection is a composable function that creates a hero section with an icon.
 * It displays the icon in a circular background with a blur effect.
 *
 * @param modifier Modifier to be applied to the hero section.
 * @param icon The ImageVector icon to be displayed in the hero section.
 * @param size The size of the icon.
 * @param contentDescription Description for accessibility purposes.
 * @param color Color of the icon.
 */
@Composable
fun BuildHeroSection(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    size: Dp,
    iconSize: Dp,
    contentDescription: String = "Hero Icon",
    color: Color = Color.White
) {
    Box(
        modifier = modifier
            .size(size)
            .background(
                Color.White.copy(alpha = 0.2f),
                CircleShape
            )
          ,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            tint = color,
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize),
        )

    }
}