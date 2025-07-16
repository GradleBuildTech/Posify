package com.example.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.lib.constants.DisplayMetric
import com.example.core.lib.constants.DrawableConst

@Composable
fun BuildAppIcon(
    imageRes: Int?, // Resource ID of the app icon image
    modifier: Modifier,
    size: Dp = 64.dp// Default size for the app icon
) {

    val shape = RoundedCornerShape(DisplayMetric.CARD_BORDER_RADIUS.dp) // Define the shape of the icon
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .shadow(4.dp) // Add shadow effect
    ) {
        Image(
            painter = painterResource(imageRes ?: DrawableConst.APP_ICON), // Replace with your app icon resource
            contentDescription = "App Icon",
            modifier = Modifier
                .fillMaxSize()
                .clip(shape) // Ensure the image respects the shape
        )

    }
}