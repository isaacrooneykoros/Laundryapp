package com.example.cleanhomes111.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun CheckoutScreen(
    navController: NavHostController,
    description: String,
    amount: Float,
    onConfirmPayment: () -> Unit // Callback to confirm the payment
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Payment Details
        Text(
            text = "Checkout",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Display Description
        Text(
            text = "Description: $description",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display Amount
        Text(
            text = "Amount: $$amount",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Confirm Payment Button
        Button(
            onClick = {
                onConfirmPayment() // Call the callback to confirm the payment
                navController.popBackStack() // Navigate back to the PaymentScreen
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Confirm Payment", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cancel Button
        Button(
            onClick = { navController.popBackStack() }, // Navigate back to the PaymentScreen
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Cancel", fontWeight = FontWeight.Bold)
        }
    }
}

@Preview
@Composable
fun CheckoutScreenPreview() {
    CheckoutScreen(
        navController = rememberNavController(),
        description = "Payment for 5 T-shirts",
        amount = 23.35f,
        onConfirmPayment = {}
    )
}

