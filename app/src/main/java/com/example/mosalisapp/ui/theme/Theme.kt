package com.example.mosalisapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.mosalisapp.ui.viewmodel.AppTheme

private val DarkColorScheme = darkColorScheme(
    primary = AntigravityCyan,
    onPrimary = IndigoMidnight,
    primaryContainer = GlowCyan,
    onPrimaryContainer = AntigravityCyan,
    secondary = AntigravityBlue,
    onSecondary = Color.White,
    background = IndigoMidnight,
    surface = DeepIndigo,
    onSurface = Color.White,
    onSurfaceVariant = SlateGrey,
    error = CoralError
)

private val LightColorScheme = lightColorScheme(
    primary = GoldPremium,
    onPrimary = OnGoldBeige,
    primaryContainer = SoftBeige,
    onPrimaryContainer = GoldPremium,
    secondary = AntigravityBlue,
    onSecondary = Color.White,
    background = AntigravityBeige,
    surface = SoftBeige,
    onSurface = IndigoMidnight,
    onSurfaceVariant = SlateGrey
)

@Composable
fun MosalisAppTheme(
    appTheme: AppTheme = AppTheme.SYSTEM,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val darkTheme = when (appTheme) {
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
        AppTheme.SYSTEM -> isSystemInDarkTheme()
    }
    
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}