package com.khidrew.myweather

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.khidrew.myweather.screens.HomeScreen
import com.khidrew.myweather.screens.WeatherScreen

@Composable
fun Navigation(padding: PaddingValues) {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.padding(paddingValues = padding),
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("weather/{location_key}/{name}/{country}", arguments = listOf(
            navArgument("location_key") {
                type = NavType.StringType
            },
            navArgument("name") {
                type = NavType.StringType
            },
            navArgument("country") {
                type = NavType.StringType
            }

        )) {
            WeatherScreen(
                navController = navController,
                locationKey = it.arguments?.getString("location_key") ?: "",
                locationName = it.arguments?.getString("name") ?: "",
                country = it.arguments?.getString("country") ?: ""
            )
        }
    }
}