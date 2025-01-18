package com.example.cleanhomes111.ui.theme.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleanhomes111.bankningappui.data.BottomNavigation
import com.example.cleanhomes111.R

@Composable
fun HomeScreen() {
    Column(modifier = Modifier
        .fillMaxHeight()
        .background(Color(0xFFF5F5F5))
        .verticalScroll(rememberScrollState())
    )
    {
        // Top Bar
        TopBar()

        // Special For You Section
        Spacer(modifier = Modifier.height(25.dp))
        SpecialForYouSection()

        // Categories Section
        Spacer(modifier = Modifier.height(16.dp))
        CategoriesSection()

        // Popular Services Section
        Spacer(modifier = Modifier.height(16.dp))
        PopularServicesSection()
        Spacer(modifier = Modifier.height(20.dp))
        // Bottom Navigation
        BottomNavigationBar()
    }
}

@Composable
fun TopBar() {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF3BA785), shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp))
            .padding(16.dp)
            .size(200.dp )
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Location",
                style = TextStyle(color = Color.White, fontSize = 12.sp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.img_2), // Replace with location icon
                    contentDescription = "Location Icon",
                    tint = Color.Yellow
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "New York, USA",
                    style = TextStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            SearchBar()
        }
    }
}

@Composable
fun SearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.img_2), // Replace with search icon
            contentDescription = "Search Icon",
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Search",
            style = TextStyle(color = Color.Gray, fontSize = 14.sp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.img_2), // Replace with filter icon
            contentDescription = "Filter Icon",
            tint = Color.Gray
        )
    }
}

@Composable
fun SpecialForYouSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "SpecialForYou",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "See All",
                style = TextStyle(fontSize = 14.sp, color = Color(0xFF3BA785)),
                modifier = Modifier.clickable {
                    //route to login screen

                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Carousel Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color.Gray, shape = RoundedCornerShape(16.dp))

        ) {
            // Replace with Image from your resources
            Image(
                painter = painterResource(id = R.drawable.img_2), // Replace with carousel image
                contentDescription = "Special Offer",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(16.dp))
            )

        }
    }
}

@Composable
fun CategoriesSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Categories",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "See All",
                style = TextStyle(fontSize = 14.sp, color = Color(0xFF3BA785))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Icons Row
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            CategoryItem(icon = R.drawable.img_2, title = "Cleaning")
            CategoryItem(icon = R.drawable.img_2, title = "Repairing")
            CategoryItem(icon = R.drawable.img_2, title = "Plumbing")
            CategoryItem(icon = R.drawable.img_2, title = "Shifting")
        }
    }
}

@Composable
fun CategoryItem(icon: Int, title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(Color(0xFFE6F4EF), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "$title Icon",
                tint = Color(0xFF3BA785)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = TextStyle(fontSize = 14.sp, color = Color(0xFF3BA785))
        )
    }
}

@Composable
fun PopularServicesSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Popular Services",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "See All",
                style = TextStyle(fontSize = 14.sp, color = Color(0xFF3BA785))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Replace with real data
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .size(140.dp, 100.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(16.dp))
            )
            Box(
                modifier = Modifier
                    .size(140.dp, 100.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(16.dp))
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
        title = "Notifications",
        icon = Icons.Rounded.Notifications
    ),

    BottomNavigation(
        title = "Account",
        icon = Icons.Rounded.AccountCircle
    )
)

@Composable
fun BottomNavigationBar() {
    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {

            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = index == 0,
                    onClick = {},
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }

        }
    }
}





@Composable
private fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Other")
    }
}





@Composable
@Preview(showBackground = true , showSystemUi = true)
fun HomeScreenPreview(){

    HomeScreen()

}

