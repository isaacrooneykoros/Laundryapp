package com.example.cleanhomes111.bankningappui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahmedapps.bankningappui.CardsSection
import com.ahmedapps.bankningappui.FinanceSection
import com.example.cleanhomes111.ui.theme.screens.CheckoutScreen
import com.example.cleanhomes111.ui.theme.screens.PaymentScreen
import com.example.cleanhomes111.ui.theme.screens.makePayment
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme { // Replace BankingAppUITheme with MaterialTheme
                val systemUiController = rememberSystemUiController()
                SetBarColor(color = MaterialTheme.colorScheme.background)
                val colorScheme = MaterialTheme.colorScheme
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = colorScheme.background
                    )
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "payment") {
                        composable("payment") {
                            PaymentScreen(navController = navController)
                        }
                        composable("checkout/{description}/{amount}") { backStackEntry ->
                            val description = backStackEntry.arguments?.getString("description") ?: ""
                            val amount = backStackEntry.arguments?.getString("amount")?.toFloatOrNull() ?: 0f
                            CheckoutScreen(
                                navController = navController,
                                description = description,
                                amount = amount,
                                onConfirmPayment = {
                                    // Add the transaction to the list
                                    val newTransaction = makePayment(description, amount)
                                    // You can use a shared ViewModel or pass the transaction back to PaymentScreen
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SetBarColor(color: Color) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = color
        )
    }
}

@Preview
@Composable
fun WalletScreen() {
    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            WalletSection()
            CardsSection()
            Spacer(modifier = Modifier.height(16.dp))
            FinanceSection()
            Spacer(modifier = Modifier.height(16.dp))
            CurrenciesSection()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
