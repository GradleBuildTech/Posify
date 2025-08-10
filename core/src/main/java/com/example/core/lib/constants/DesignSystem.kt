package com.example.core.lib.constants

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.core.lib.montserratFontFamily

/**
 * DesignSystem.kt
 * This file defines the text styles used throughout the application.
 * It includes styles for titles, subtitles, and body text.
 * These styles are used to maintain consistency in typography across the app.
 */
object DesignSystem {

    ///Title small
    val TITLE_SMALL_STYLE = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.15.sp,
        fontFamily = montserratFontFamily
    )

    ///Title medium
    val TITLE_MEDIUM_STYLE = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.15.sp,
        fontFamily = montserratFontFamily
    )

    ///Title large
    val TITLE_LARGE_STYLE = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.15.sp,
        fontFamily = montserratFontFamily
    )

    ///Subtitle small
    val SUBTITLE_SMALL_STYLE = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.sp,
        fontFamily = montserratFontFamily
    )

    ///Subtitle medium
    val SUBTITLE_MEDIUM_STYLE = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.sp,
        fontFamily = montserratFontFamily
    )

    ///Subtitle large
    val SUBTITLE_LARGE_STYLE = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.sp,
        fontFamily = montserratFontFamily
    )

    ///Body small
    val BODY_SMALL_STYLE = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.sp,
        fontFamily = montserratFontFamily
    )

    ///Body medium
    val BODY_MEDIUM_STYLE = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.sp,
        fontFamily = montserratFontFamily
    )

    ///Body large
    val BODY_LARGE_STYLE = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.sp,
        fontFamily = montserratFontFamily
    )

    ///Caption
    val CAPTION_STYLE = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.4.sp,
        fontFamily = montserratFontFamily
    )

    ///Button
    val BUTTON_STYLE = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.25.sp,
        fontFamily = montserratFontFamily
    )

    //Big
    val BIG_TITLE_STYLE = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.15.sp,
        fontFamily = montserratFontFamily
    )

}