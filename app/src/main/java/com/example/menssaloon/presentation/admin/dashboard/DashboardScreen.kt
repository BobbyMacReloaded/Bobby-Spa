package com.example.menssaloon.presentation.admin.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.menssaloon.R
import com.example.menssaloon.presentation.admin.appointment.AppointmentScreenViewModel
import com.example.menssaloon.presentation.admin.barber_screen.BarberListScreenViewModel
import com.example.menssaloon.presentation.admin.service_screen.ServiceListViewModel
import com.example.menssaloon.presentation.theme.BlueDark
import com.example.menssaloon.presentation.theme.YellowDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onAppointmentClick: () -> Unit,
    onBarbersClick: () -> Unit,
    onEditServicesClick: () -> Unit,
    onSignOutClick: () -> Unit,
    dashboardViewModel: DashboardViewmodel = hiltViewModel(),
    serviceListViewModel: ServiceListViewModel = hiltViewModel(),
    barberListScreenViewModel: BarberListScreenViewModel = hiltViewModel(),
    appointmentListViewModel: AppointmentScreenViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    val state by dashboardViewModel.state.collectAsState()
    val services = serviceListViewModel.serviceList.collectAsState()
    val barbers = barberListScreenViewModel.barberList.collectAsState()
    val appointment = appointmentListViewModel.appointmentList.collectAsState()
    LaunchedEffect(Unit) {

        dashboardViewModel.fetchData(barbers.value, services.value,appointment.value )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Dashboard",
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onSignOutClick() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }
                    DropDownSession(
                        expanded = expanded,
                        onDismiss = { expanded = false },
                        onAppointmentClick = onAppointmentClick,
                        onBarbersClick = onBarbersClick,
                        onEditServicesClick = onEditServicesClick
                    )
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BlueDark)
                    .padding(paddingValues)
                    .padding(6.dp)
            ) {
                CardSection(
                    serviceCount = state.allServicesCount,
                    barberCount = state.barbersAvailableCount,
                    pendingRequestCount = state.pendingRequestsCount
                )
            }
        }
    )
}

@Composable
fun CardSection(
    modifier: Modifier = Modifier,
    serviceCount: Int,
    barberCount: Int,
    pendingRequestCount: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DashboardCard(
            title = "All Services",
            icon = painterResource(id = R.drawable.ic_service),
            count = serviceCount,
            backgroundColor = YellowDark
        )
        DashboardCard(
            title = "Barbers Available",
            icon = Icons.Default.Person,
            count = barberCount,
            backgroundColor = YellowDark
        )
        DashboardCard(
            title = "Pending Requests",
            icon = Icons.Default.DateRange,
            count = pendingRequestCount,
            backgroundColor = BlueDark
        )
    }
}

@Composable
fun DashboardCard(
    title: String,
    icon: Any,
    count: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(120.dp)
            .padding(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                if (icon is Int) {
                    Icon(painter = painterResource(id = icon), contentDescription = null, tint = Color.White)
                } else if (icon is androidx.compose.ui.graphics.vector.ImageVector) {
                    Icon(imageVector = icon, contentDescription = null, tint = Color.White)
                }
            }
            Text(title, fontWeight = FontWeight.Bold)
            Text("$count", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DropDownSession(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onAppointmentClick: () -> Unit,
    onBarbersClick: () -> Unit,
    onEditServicesClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        DropdownMenuItem(
            text = { Text("Appointments") },
            onClick = {
                onAppointmentClick()
                onDismiss()
            }
        )
        DropdownMenuItem(
            text = { Text("Barbers") },
            onClick = {
                onBarbersClick()
                onDismiss()
            }
        )
        DropdownMenuItem(
            text = { Text("Edit Services") },
            onClick = {
                onEditServicesClick()
                onDismiss()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    DashboardScreen(
        onAppointmentClick = {},
        onBarbersClick = {},
        onEditServicesClick = {},
        onSignOutClick = {}
    )
}
