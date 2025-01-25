package com.example.menssaloon.presentation.user.welcome_page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.menssaloon.R
import com.example.menssaloon.presentation.theme.BlueDark
import com.example.menssaloon.presentation.theme.YellowDark
import com.example.menssaloon.presentation.user.LoginDialog
import com.example.menssaloon.presentation.user.booking_screen.BookingScreen
import com.example.menssaloon.presentation.user.our_services_screen.OurServicesScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomePage(
    onLoginClick: () -> Unit,
    onBookingClicked: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val tabs = listOf("Welcome", "Our Services", "Booking")
    val pagerState = rememberPagerState(pageCount = { tabs.size }) // Pager state to control pages
    val coroutineScope = rememberCoroutineScope()
    var isLoginDialogOpen by remember { mutableStateOf(false) }
    val loginErrorMessage by remember { mutableStateOf("") }
    val loginState by loginViewModel.loginState.collectAsState()

    if (loginState.isLoginSuccessful) {
        onLoginClick()
    } else {
        // Handle the case where login is unsuccessful
        Text("Only admins allowed")
    }

    LoginDialog(
        isOpen = isLoginDialogOpen,
        onLoginSuccess = {
            // Handle successful login, e.g., navigate to another screen
            isLoginDialogOpen = false
            onLoginClick()
        },

        loginViewModel = loginViewModel,
        onDismissRequest = {isLoginDialogOpen  =false}
    )

    // If login fails, show error message
    if (loginErrorMessage.isNotEmpty()) {
        Text(text = loginErrorMessage, color = Color.Red)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueDark),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Welcome To Bobby Spa",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .clickable {isLoginDialogOpen = true }
                        .padding(vertical = 8.dp)
                        .clip(CircleShape)
                        .size(50.dp) // Ensure enough size for visibility
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally, // Ensure the content is centered horizontally
                        verticalArrangement = Arrangement.Center, // Center the content vertically
                        modifier = Modifier.fillMaxSize() // Ensure the column takes the full size of the Box
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.avatar1),
                            contentDescription = "Login Avatar",
                            contentScale = ContentScale.Crop, // Crop to fill the circle
                            modifier = Modifier
                                .size(30.dp) // Set a fixed size for the image
                        )
                        Text(
                            "Login",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(top = 4.dp) // Add padding for spacing
                        )
                    }
                }
            }

            // TabRow for Tabs
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BlueDark),
                edgePadding = 8.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch { pagerState.animateScrollToPage(index) }
                        },
                        text = {
                            Text(
                                text = title,
                                fontSize = 16.sp,
                                color = if (pagerState.currentPage == index) YellowDark else Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )
                }
            }

            // Pager Content
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> WelcomePageContent(pagerState, coroutineScope)
                    1 -> OurServicesScreen()
                    2 -> BookingScreen(onBookingClicked = { onBookingClicked() })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun WelcomePageContent(
    pagerState: PagerState,
    coroutineScope: CoroutineScope
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueDark),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bobbyspa),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(
                text = "Welcome to Bobby Spa",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(2)
                    }
                },
                shape = RectangleShape,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = YellowDark)
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
private fun WelcomePreview() {
    WelcomePage(
        onLoginClick = {},
        onBookingClicked = {}
    )
}
