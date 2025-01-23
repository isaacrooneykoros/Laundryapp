package com.example.cleanhomes111.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ahmedapps.bankningappui.CardsSection
import com.ahmedapps.bankningappui.FinanceSection
import com.example.cleanhomes111.bankningappui.CurrenciesSection
import com.example.cleanhomes111.bankningappui.WalletSection


@Composable
fun WalletScreen(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
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
        }
    }
}




@Composable
@Preview(showBackground = true, showSystemUi = true)
fun WalletScreenPreview() {
    val navController = rememberNavController()
    WalletScreen(navController = navController)
}
