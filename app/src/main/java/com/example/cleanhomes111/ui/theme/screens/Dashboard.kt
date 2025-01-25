@file:Suppress("DEPRECATION")

package com.example.cleanhomes111.ui.theme.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cleanhomes111.R
import com.example.cleanhomes111.bankningappui.items
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.Locale

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    var location by remember { mutableStateOf("Fetching location...") }

    // Request location permissions
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                startLocationUpdates(context) { fetchedLocation ->
                    location = fetchedLocation
                }
            } else {
                location = "Location permission denied"
            }
        }
    )

    // Check for location permissions and start updates
    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                startLocationUpdates(context) { fetchedLocation ->
                    location = fetchedLocation
                }
            }
            else -> {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    // Handle lifecycle events to stop location updates when the composable is disposed
    DisposableEffect(Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (loc in locationResult.locations) {
                    val address = getReadableAddress(context, loc.latitude, loc.longitude)
                    location = address ?: "Location not available"
                }
            }
        }

        val locationRequest = LocationRequest.create().apply {
            interval = 10000 // Update interval in milliseconds
            fastestInterval = 5000 // Fastest update interval
            priority = PRIORITY_HIGH_ACCURACY
        }

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }

        onDispose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xFFF5F5F5))
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        TopBar(location)

        // Special For You Section
        Spacer(modifier = Modifier.height(25.dp))
        SpecialForYouSection(navController)

        // Categories Section
        Spacer(modifier = Modifier.height(16.dp))
        CategoriesSection(navController)

        // Popular Services Section
        Spacer(modifier = Modifier.height(16.dp))
        PopularServicesSection(navController)

        Spacer(modifier = Modifier.height(62.dp))

        // Bottom Navigation
        BottomNavigationBar(navController)
    }
}

@Composable
fun TopBar(location: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
            )
            .paint(
                painterResource(id = R.drawable.backgroundhomepage),
                contentScale = ContentScale.Fit
            )
            .size(200.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(R.string.location_label),
                style = TextStyle(color = Color.White, fontSize = 12.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.location),
                    contentDescription = stringResource(R.string.location_icon_description),
                    tint = Color.Yellow,
                    modifier = Modifier.size(26.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = location,
                    style = TextStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            SearchBar()
        }
    }
}

// Function to start location updates
private fun startLocationUpdates(context: Context, onLocationFetched: (String) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationRequest = LocationRequest.create().apply {
        interval = 10000 // Update interval in milliseconds
        fastestInterval = 5000 // Fastest update interval
        priority = PRIORITY_HIGH_ACCURACY
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                val address = getReadableAddress(context, location.latitude, location.longitude)
                onLocationFetched(address ?: "Location not available")
            }
        }
    }

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }
}

// Function to convert latitude and longitude into a readable address
private fun getReadableAddress(context: Context, latitude: Double, longitude: Double): String? {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses?.isNotEmpty() == true) {
            val address = addresses[0]
            // Extract the locality (city) and sub-locality (area) if available
            val locality = address.locality ?: ""
            val subLocality = address.subLocality ?: ""
            if (locality.isNotEmpty() && subLocality.isNotEmpty()) {
                "$subLocality, $locality" // e.g., "Karen, Nairobi"
            } else {
                address.getAddressLine(0) ?: "Unknown Location"
            }
        } else {
            "Unknown Location"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "Failed to fetch address"
    }
}

@Composable
fun SearchBar() {
    Spacer(modifier = Modifier.height(40.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.search),
            contentDescription = stringResource(R.string.search_icon_description),
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.search_hint),
            style = TextStyle(color = Color.Gray, fontSize = 14.sp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.filter),
            contentDescription = stringResource(R.string.filter_icon_description),
            tint = Color.Gray,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
fun SpecialForYouSection(navController: NavHostController) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.special_for_you),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.see_all),
                style = TextStyle(fontSize = 14.sp, color = Color(0xFF3BA785)),
                modifier = Modifier.clickable {
                    navController.navigate("specialForYouScreen")
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color.Gray, shape = RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_4),
                contentDescription = stringResource(R.string.special_offer_image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(16.dp))
            )
        }
    }
}

@Composable
fun CategoriesSection(navController: NavHostController) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.categories),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.see_all),
                style = TextStyle(fontSize = 14.sp, color = Color(0xFF3BA785)),
                modifier = Modifier.clickable {
                    navController.navigate("categoriesScreen")
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            CategoryItem(
                icon = R.drawable.suitdrycleaning,
                title = stringResource(R.string.dry_cleaning)
            )
            CategoryItem(icon = R.drawable.polish, title = stringResource(R.string.shoe_cleaning))
            CategoryItem(icon = R.drawable.laundrybasket, title = stringResource(R.string.wash_and_fold))
            CategoryItem(icon = R.drawable.deliverytruck, title = stringResource(R.string.delivery))
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
                contentDescription = title,
                tint = Color.Unspecified
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
fun PopularServicesSection(navController: NavHostController) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.popular_services),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.see_all),
                style = TextStyle(fontSize = 14.sp, color = Color(0xFF3BA785)),
                modifier = Modifier.clickable {
                    navController.navigate("popularServicesScreen")
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .size(140.dp, 100.dp)
                    .paint(
                        painterResource(id = R.drawable.laundryadvert),
                        contentScale = ContentScale.FillBounds
                    )
            )
            Box(
                modifier = Modifier
                    .size(140.dp, 100.dp)
                    .paint(
                        painterResource(id = R.drawable.washandfold),
                        contentScale = ContentScale.FillBounds
                    )
            )
        }
    }
}

@SuppressLint("AutoboxingStateValueProperty")
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
                        0 -> {
                            if (navController.currentDestination?.route != "home") {
                                navController.navigate("home") {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                        1 -> {
                            if (navController.currentDestination?.route != "wallet") {
                                navController.navigate("wallet") {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                        2 -> {
                            if (navController.currentDestination?.route != "cart") {
                                navController.navigate("cart") {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                        3 -> {
                            if (navController.currentDestination?.route != "account") {
                                navController.navigate("account") {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
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

@Composable
@Preview()
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
