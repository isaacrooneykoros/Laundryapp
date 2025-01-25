package com.example.cleanhomes111.ui.theme.navigation

// In a new file called SharedPreferencesHelper.kt// In your AppNavHost.kt file

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cleanhomes111.ui.theme.screens.*
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = if (SharedPreferencesHelper.isOnboardingCompleted(LocalContext.current)) ROUT_HOME else ROUTE_ONBOARDING
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUT_HOME) {
            HomeScreen(navController = navController)
        }
        composable(ROUT_SIGNUP) {
            SignupScreen(navController = navController)
        }
        composable(ROUT_LOGIN) {
            LoginScreen(navController = navController)
        }
        composable(ROUTE_WALLET) {
            WalletScreen(navController = navController)
        }
        composable(ROUTE_DETAILSCREEN) {
            DetailScreen(navController = navController)
        }
        composable(ROUTE_PAYMENT) {
            PaymentScreen(navController = navController)
        }
        composable(ROUTE_CHECKOUT) {
            CheckoutScreen(
                navController = rememberNavController(),
                description = "Payment for 5 T-shirts",
                amount = 23.35f,
                onConfirmPayment = {}
            )
        }
        composable(ROUTE_ONBOARDING) {
            OnboardingScreen(navController = navController)
        }
        composable(ROUTE_SPLASH) {
            SplashScreen(navController = navController)
        }
    }
}
