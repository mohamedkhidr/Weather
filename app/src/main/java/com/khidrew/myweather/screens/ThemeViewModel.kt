package com.khidrew.myweather.screens

import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


val LocalTheme = compositionLocalOf<ThemeViewModel> { error("No viewModel provided yet") }
class ThemeViewModel: ViewModel() {
    private val _darkModeEnabled:MutableStateFlow<Boolean> = MutableStateFlow(false)
    val darkModeEnabled = _darkModeEnabled.asStateFlow()



    fun toggleDarkMode(){
        _darkModeEnabled.update { !_darkModeEnabled.value }
    }
}