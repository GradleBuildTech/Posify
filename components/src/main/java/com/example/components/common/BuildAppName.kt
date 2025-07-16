package com.example.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.example.core.lib.constants.DesignSystem

private const val FIRST_NAME = "Posify"
private const val SECOND_NAME = "System"

enum class AppNameType  {
    ONE_LINE,
    TWO_LINE,
}


/**
 * Composable function to build the app name with customizable styles and colors.
 *
 * @param appNameType The type of app name layout (one line or two lines).
 * @param modifier Modifier for styling the composable.
 * @param textColor Color for the second part of the app name.
 * @param primaryColor Color for the first part of the app name.
 * @param textStyle Optional custom text style for the app name.
 */

@Composable
fun BuildAppName(
    appNameType: AppNameType = AppNameType.ONE_LINE,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle? = null
) {
    val style = textStyle ?: DesignSystem.TITLE_SMALL_STYLE

    when (appNameType) {
        AppNameType.ONE_LINE -> {
            Text(
                modifier = modifier,
                text = buildAnnotatedString(
                    builder = {
                        withStyle(
                            SpanStyle(color = textColor, fontSize = style.fontSize) // Default size if not specified
                        ){
                            append(FIRST_NAME)
                        }
                        append(" ")
                        withStyle(
                            SpanStyle(color = primaryColor, fontSize = style.fontSize) // Default size if not specified
                        ){
                            append(SECOND_NAME)
                        }
                    }
                ),
                style = style
            )
        }
        AppNameType.TWO_LINE -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                Text(
                    text = FIRST_NAME,
                    style = style.copy(color = primaryColor),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = SECOND_NAME,
                    style = style.copy(color = textColor),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}
