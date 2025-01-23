package com.example.cleanhomes111.ui.theme.screens

import BottomNavigationBar
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cleanhomes111.bankningappui.data.BottomNavigation


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeApppScreen(navController: NavController) {
    Scaffold(
        topBar = { TopBar() },
        content = {
            Column {
                SpecialOfferSection()
                CategoriesSection()
                PopularServicesSection()
            }
        },
        bottomBar = {  BottomNavigationBar(navController = NavController) }
    )
}


@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "New York, USA", style = MaterialTheme.typography.bodyMedium)
        Icon(
            painter = painterResource(id = com.example.cleanhomes111.R.drawable.chooseus),
            contentDescription = "Notification",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun SpecialOfferSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "#SpecialForYou", style = MaterialTheme.typography.displayMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Black)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Limited time!", color = Color.White)
                Text(text = "Get Special Offer", color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = "Up to 40%", color = Color.White, fontSize = 24.sp)
                Text(text = "All Services Available | T&C Applied", color = Color.White, fontSize = 12.sp)
            }
            Button(onClick = { /* Handle click */ }) {
                Text(text = "Claim")
            }
        }
    }
}

@Composable
fun CategoriesSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Categories", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryItem(name = "Cleaning", icon = com.example.cleanhomes111.R.drawable.chooseus)
            CategoryItem(name = "Repairing", icon = com.example.cleanhomes111.R.drawable.chooseus)
            CategoryItem(name = "Plumbing", icon = com.example.cleanhomes111.R.drawable.chooseus)
            CategoryItem(name = "Shifting", icon = com.example.cleanhomes111.R.drawable.chooseus)
        }
    }
}

@Composable
fun CategoryItem(name: String, icon: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = name,
            modifier = Modifier.size(48.dp)
        )
        Text(text = name)
    }
}

@Composable
fun PopularServicesSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Popular Services", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ServiceItem(name = "Service 1", rating = 4.8, image = com.example.cleanhomes111.R.drawable.chooseus)
            ServiceItem(name = "Service 2", rating = 4.9, image = com.example.cleanhomes111.R.drawable.chooseus)
        }
    }
}

@Composable
fun ServiceItem(name: String, rating: Double, image: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = image),
            contentDescription = name,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Text(text = name)
        Text(text = "$rating")
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    // State to track the selected item
    val selectedItem = remember { mutableIntStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem.intValue == index,
                onClick = {
                    // Update the selected item
                    selectedItem.intValue = index

                    // Handle navigation
                    when (index) {
                        0 -> navController.navigate("home")
                        1 -> navController.navigate("wallet")
                        2 -> navController.navigate("cart")
                        3 -> navController.navigate("accountScreen")
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (selectedItem.intValue == index) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        }
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (selectedItem.value == index) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        }
                    )
                }
            )
        }
    }
}

val items = listOf(
    BottomNavigation(
        title = "Home",
        icon = Icons.Rounded.Home
    ),
    BottomNavigation(
        title = "Wallet",
        icon = Icons.Rounded.Wallet
    ),
    BottomNavigation(
        title = "Cart",
        icon = Icons.Rounded.ShoppingCart
    ),
    BottomNavigation(
        title = "Account",
        icon = Icons.Rounded.AccountCircle
    )
)
@Composable
@Preview()
fun HomeApppScreenPreview() {
    val navController = rememberNavController()
    HomeApppScreen(navController = navController)
}
