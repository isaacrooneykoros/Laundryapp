package com.example.cleanhomes111.ui.theme.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            OnboardingScreen(navController = navController)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(navController: NavHostController) {
    val pagerState = rememberPagerState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCFFFC)) // Light grey background
    ) {
        HorizontalPager(
            count = onboardingItems.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingCard(
                item = onboardingItems[page],
                pagerState = pagerState,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingCard(
    item: OnboardingItem,
    pagerState: PagerState,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Image with a curved bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Curved divider
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter)
            ) {
                val path = Path().apply {
                    moveTo(0f, 0f)
                    quadraticBezierTo(size.width / 2, size.height * 2, size.width, 0f)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(path, Color(0xFFF5F5F5))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Dots indicator
            Row(
                modifier = Modifier.padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                repeat(onboardingItems.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == pagerState.currentPage) Color(0xFF007BFF) else Color(
                                    0xFFD9D9D9
                                )
                            )
                    )
                }
            }

            // Title
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            // Subtitle
            if (item.subTitle.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.subTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    color = Color(0xFF555555),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(50.dp))



            // Description
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                color = Color(0xFF777777),
                lineHeight = 20.sp,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Button
            if (pagerState.currentPage == onboardingItems.size - 1) {
                // "Get Started" button on the last screen
                Button(
                    onClick = {
                        navController.navigate("home") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A7C32)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = "GET STARTED",
                        color = Color(0xFF333333),
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                // "Next" button for other screens
                Button(
                    onClick = {
                        // Handle page change directly in the click handler
                        kotlinx.coroutines.MainScope().launch {
                            pagerState.animateScrollToPage(
                                page = (pagerState.currentPage + 1).coerceAtMost(onboardingItems.size - 1),
                                animationSpec = androidx.compose.animation.core.tween(
                                    durationMillis = 800,
                                    easing = androidx.compose.animation.core.FastOutSlowInEasing
                                )
                            )
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B621E)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = "NEXT",
                        color = Color(0xFF333333),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Sample Data
data class OnboardingItem(
    val imageRes: Int,
    val title: String,
    val subTitle: String = "", // Added optional subtitle
    val description: String,
    val isActive: Boolean
)

val onboardingItems = listOf(
    OnboardingItem(
        imageRes = com.example.cleanhomes111.R.drawable.onboarding1, // Replace with your drawable resource
        title = "Welcome To MamaFua Laundry",
        subTitle = "Effortless laundry at your fingertips.", // Added subtitle
        description = " Together lets start  Your Laundry Journey.",
        isActive = true
    ),
    OnboardingItem(
imageRes = com.example.cleanhomes111.R.drawable.fold,
        title = "Convenient Scheduling",
        subTitle = "Easy Scheduling", // Added subtitle
        description = "Scheduling has never been this easy and flexible!Choose a time that works best for you, and we’ll pick up your laundry.",
        isActive = false
    ),
    OnboardingItem(
        imageRes = com.example.cleanhomes111.R.drawable.payment, // Replace with your drawable resource
        title = "Affordable & Reliable",
        subTitle = "Pocket-Friendly Prices", // Added subtitle
        description = "Save money and get the best services in town.Get top-notch service without breaking the bank",
        isActive = false
    ),
    OnboardingItem(
        imageRes = com.example.cleanhomes111.R.drawable.whymamafua, // Replace with your drawable resource
        title = "Why Choose MamaFua?",
        description = "Save Time: Focus on what matters most.\n" +
                "\n" +
                "Stress-Free Service: Reliable and professional.\n" +
                "\n" +
                "Satisfaction Guaranteed: Quality you can trust.",
        isActive = false
    ),
    OnboardingItem(
        imageRes = com.example.cleanhomes111.R.drawable.chooseus, // Replace with your drawable resource
        title = "Get Started With MamaFua Laundry",
        subTitle = "Effortless laundry at your fingertips.", // Added subtitle
        description = "We're thrilled to have you with us! Whether you're looking to save time or just want your laundry done right, we’ve got you covered. Let’s get started on making laundry day easier and more convenient for you.",
        isActive = false
    )

)

@Composable
@Preview(showBackground = true)
fun OnboardingScreenPreview() {
    OnboardingScreen(navController = rememberNavController())

}
