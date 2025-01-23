package com.example.cleanhomes111.bankningappui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DryCleaning
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Transaction(
    val id: Int,
    val description: String,
    val amount: Float,
    val date: String, // Formatted date
    val icon: ImageVector
)

class TransactionsViewModel : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> get() = _transactions

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            _transactions.value = _transactions.value + transaction
        }
    }

    fun makePayment(description: String, amount: Float): Transaction {
        val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        val date = dateFormat.format(Date())
        return Transaction(
            id = _transactions.value.size + 1,
            description = description,
            amount = amount,
            date = date,
            icon = Icons.Rounded.DryCleaning
        )
    }
}

