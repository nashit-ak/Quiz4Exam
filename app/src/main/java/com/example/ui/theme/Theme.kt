package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
    darkColorScheme(
        primary = VibrantBlue, // #0066FF
        onPrimary = Color.White,
        primaryContainer = NavyDark,
        onPrimaryContainer = Color.White,
        secondary = VibrantBlue,
        onSecondary = Color.White,
        background = Color(0xFF0F172A),
        onBackground = Color.White,
        surface = Color(0xFF1E293B),
        onSurface = Color.White,
        surfaceVariant = Color(0xFF334155),
        onSurfaceVariant = Color(0xFFCBD5E1),
        outline = Color(0xFF475569),
    )

private val LightColorScheme =
    lightColorScheme(
        primary = VibrantBlue, // #0066FF
        onPrimary = Color.White,
        primaryContainer = IceBlue,
        onPrimaryContainer = NavyDark,
        secondary = VibrantBlue,
        onSecondary = Color.White,
        background = OffWhiteBackground,
        onBackground = TextPrimaryNavy,
        surface = SurfacePureWhite,
        onSurface = TextPrimaryNavy,
        surfaceVariant = Color(0xFFF1F5F9),
        onSurfaceVariant = TextSecondaryMuted,
        outline = CardBorder,
    )

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
