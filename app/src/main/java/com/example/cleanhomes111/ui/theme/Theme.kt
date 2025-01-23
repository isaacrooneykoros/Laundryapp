package com.example.cleanhomes111.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.cleanhomes111.R

// Define the Roboto font family
val Roboto = FontFamily(
    listOf(
        Font(R.font.roboto_regular),
        Font(R.font.roboto_medium, FontWeight.W500),
        Font(R.font.roboto_bold, FontWeight.Bold)
    )
)

// Set up the typography to use the Roboto font family
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    // Add other text styles as needed
)


@Composable
fun CleanHomes111Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes(),
        content = content
    )
}

// Define the colors used in the app
val Blue = Color(0xFF64B5F6)
val LightBlue = Color(0xFFBBDEFB)
val DarkBlue = Color(0xFF1976D2)
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val LightGray = Color(0xFFE0E0E0)
val Gray = Color(0xFF9E9E9E)
val DarkGray = Color(0xFF424242)
val Red = Color(0xFFEF5350)
val Green = Color(0xFF66BB6A)
val Yellow = Color(0xFFFFD54F)
val Orange = Color(0xFFFFA726)
val Purple = Color(0xFF8E24AA)
val Pink = Color(0xFFEC407A)
val Teal = Color(0xFF26A69A)
val Cyan = Color(0xFF00BCD4)
val Amber = Color(0xFFFFC107)
val Brown = Color(0xFF8D6E63)
val Indigo = Color(0xFF5C6BC0)
val Lime = Color(0xFFD4E157)
val DeepOrange = Color(0xFFFF7043)
val DeepPurple = Color(0xFF7E57C2)



