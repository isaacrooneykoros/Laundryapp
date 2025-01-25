package com.example.cleanhomes111.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.cleanhomes111.R
import com.example.cleanhomes111.ui.theme.navigation.ROUTE_ONBOARDING
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Replace with your splash logo
            contentDescription = "Splash Logo"
        )

    }

    LaunchedEffect(Unit) {
        delay(3000) // Delay for 3 seconds
        navController.navigate(ROUTE_ONBOARDING) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }
}
@Preview
@Composable
fun Splashscreenpreview(){
    SplashScreen(navController = NavController(LocalContext.current))
}



