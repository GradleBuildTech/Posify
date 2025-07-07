package com.example.core.lib.constants

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * DesignSystem.kt
 * This file defines the text styles used throughout the application.
 * It includes styles for titles, subtitles, and body text.
 * These styles are used to maintain consistency in typography across the app.
 */
object DesignSystem {

    ///Title small
    val TITLE_SMALL_STYLE = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.15.sp
    )

    ///Title medium
    val TITLE_MEDIUM_STYLE = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.15.sp
    )

    ///Title large
    val TITLE_LARGE_STYLE = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.15.sp
    )

    ///Subtitle small
    val SUBTITLE_SMALL_STYLE = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.sp
    )

    ///Subtitle medium
    val SUBTITLE_MEDIUM_STYLE = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.sp
    )

    ///Subtitle large
    val SUBTITLE_LARGE_STYLE = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.sp
    )

    ///Body small
    val BODY_SMALL_STYLE = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.sp
    )


}