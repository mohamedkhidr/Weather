package com.khidrew.myweather.ui.theme

import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.khidrew.myweather.screens.LocalTheme
import com.khidrew.myweather.screens.ThemeViewModel

private val DarkColorScheme = darkColorScheme(
    primary = Dark200,
    secondary = Dark500,
)

private val LightColorScheme = lightColorScheme(
    primary = Dark200,
    secondary = Dark500,
)

@Composable
fun MyWeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {


    val themeViewModel: ThemeViewModel = viewModel()
    val localValue = compositionLocalOf { error("No value provided") }
    val darkModeEnabled by themeViewModel.darkModeEnabled.collectAsState()
    val primaryColor by animateColorAsState(
        targetValue = if (darkModeEnabled) Dark200 else Light200,
        animationSpec = tween(500)
    )
    val secondaryColor by animateColorAsState(
        targetValue = if (darkModeEnabled) Dark500 else Light500,
        animationSpec = tween(500)
    )
//    val textColor by animateColorAsState(targetValue = if(darkTheme) Color.White else Color.Black, animationSpec = tween(500))
//    val textColorSmall by animateColorAsState(targetValue = if(darkTheme) Color.Gray else Color.DarkGray, animationSpec = tween(500))


    val colors = darkColorScheme(
        primary = primaryColor,
        secondary = secondaryColor
    )
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> colors
        else -> colors
    }

    rememberSystemUiController().isSystemBarsVisible = false
    CompositionLocalProvider(LocalTheme provides themeViewModel) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}