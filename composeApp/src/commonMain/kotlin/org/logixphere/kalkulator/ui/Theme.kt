package org.logixphere.kalkulator.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPallete = darkColorScheme(
    primary = colorPrimary,
    primaryContainer = Color(0xFF045D56),
    secondary = Color(0xFFAFFFFB),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    background = colorDarkBackground,
    tertiary = colorDark
)

private val LightColorPallete = lightColorScheme(
    primary = colorPrimary,
    primaryContainer = Color(0xFF045D56),
    secondary = Color(0xFF002910),
    onPrimary = Color.Black,
    onSecondary = Color.White,
    background = colorBackgroundLight,
    tertiary = Color.White,
)

@Composable
fun CalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if(darkTheme) {
        DarkColorPallete
    } else {
        LightColorPallete
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}