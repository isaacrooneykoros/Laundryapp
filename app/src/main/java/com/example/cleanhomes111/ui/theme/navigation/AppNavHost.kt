package com.example.cleanhomes111.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cleanhomes111.ui.theme.screens.HomeScreen
import com.example.cleanhomes111.ui.theme.screens.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {SplashScreen(navController) }
        //composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
    }
}
