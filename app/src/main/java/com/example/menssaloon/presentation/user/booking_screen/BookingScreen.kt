package com.example.menssaloon.presentation.user.booking_screen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.menssaloon.data.mpesa_api.NetworkResponse
import com.example.menssaloon.domain.model.Service
import com.example.menssaloon.presentation.admin.dashboard.DashboardEvent
import com.example.menssaloon.presentation.admin.dashboard.DashboardViewmodel
import com.example.menssaloon.presentation.admin.service_screen.ServiceListViewModel
import com.example.menssaloon.presentation.theme.BlueDark
import com.example.menssaloon.presentation.theme.YellowDark
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BookingScreen(
    onBookingClicked: () -> Unit,
    viewModel: BookingScreenViewModel = hiltViewModel(),
    serviceViewModel: ServiceListViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewmodel = hiltViewModel()
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var scheduledDate by remember { mutableStateOf("") }
    val services by serviceViewModel.serviceList.collectAsState(initial = emptyList())
    val state = viewModel.state
    val networkState by viewModel.networkResponse.collectAsState(initial = NetworkResponse.Loading)

    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        calendar.set(year, month, dayOfMonth)
        scheduledDate = dateFormatter.format(calendar.time)
        viewModel.onEvent(BookingScreenEvents.EnteredScheduledDate(calendar.time))
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            if (event is UIEvent.ShowToast) {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueDark)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = state.fullName,
            onValueChange = { viewModel.onEvent(BookingScreenEvents.EnteredName(it)) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.onEvent(BookingScreenEvents.EnteredEmail(it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.phoneNumber,
            onValueChange = { viewModel.onEvent(BookingScreenEvents.EnteredPhoneNumber(it)) },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = scheduledDate,
            onValueChange = { /* Read-only field */ },
            label = { Text("Scheduled Date") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    DatePickerDialog(
                        context,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                }
            }
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(services) { service ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = state.selectedServices.contains(service),
                        onCheckedChange = {
                            viewModel.onEvent(BookingScreenEvents.ToggleServiceSelection(service))
                        }
                    )
                    Text(
                        text = "${service.name} - Ksh ${service.cost}",
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        Text(
            text = "Total Cost: Ksh ${state.totalCost}",
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )

        Button(
            onClick = {
                viewModel.onEvent(BookingScreenEvents.Submit)
                onBookingClicked()
                dashboardViewModel.onEvent(DashboardEvent.IncrementPendingRequests)


            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = YellowDark)
        ) {
            Text(
                text = "Confirm Booking",
                color = Color.Blue
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookingScreenPreview() {
    BookingScreen(
        onBookingClicked = {}
    )
}
