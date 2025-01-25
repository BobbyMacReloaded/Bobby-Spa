package com.example.menssaloon.presentation.admin.appointment

import com.example.menssaloon.data.mpesa_api.api.MpesaViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.menssaloon.domain.model.Appointment
import com.example.menssaloon.presentation.admin.dashboard.DropDownSession
import com.example.menssaloon.presentation.theme.BlueDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentScreen(
    onBackClick: () -> Unit,
    onBarbersClick: () -> Unit,
    onDropClick: (Int?) -> Unit,
    onEditServicesClick: () -> Unit,
    viewModel: AppointmentScreenViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var showDetailsDialog by remember { mutableStateOf(false) }

    val appointmentList by viewModel.appointmentList.collectAsState()
    val selectedAppointment by viewModel.selectedAppointment.collectAsState()
val mpesaviewModel  = viewModel<MpesaViewModel>()
    AppointmentDetailsDialog(
        showDetailsDialog = showDetailsDialog,
        onDismissRequest = { showDetailsDialog = false },
        appointment = selectedAppointment,
        onVerifyClick = { appointmentId, newStatus ->
            viewModel.updateStatus(appointmentId, newStatus)
            showDetailsDialog = false
        },
        viewModel = mpesaviewModel
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Appointments",
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }
                    DropDownSession(
                        expanded = expanded,
                        onDismiss = { expanded = false },
                        onAppointmentClick = {},
                        onBarbersClick = onBarbersClick,
                        onEditServicesClick = onEditServicesClick
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BlueDark)
        ) {
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                items(appointmentList) { appointment ->
                    AppointmentItem(
                        appointment = appointment,
                        onDropClick = {
                            viewModel.selectAppointment(appointment.appointmentId)
                            showDetailsDialog = true
                        },
                        onDeleteClick = {
                            viewModel.deleteAppointment(appointment)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AppointmentItem(
    appointment: Appointment,
    onDropClick: () -> Unit,
    onDeleteClick: () -> Unit,
    viewModel: AppointmentScreenViewModel = hiltViewModel()
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AppointmentDeleteDialog(
            onConfirmClick = {
                showDeleteDialog = false
                onDeleteClick()
            },
            onDismissClick = { showDeleteDialog = false }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text("Date Created", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(appointment.dateCreated.toString(), style = MaterialTheme.typography.bodySmall)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text("Full Name", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(appointment.fullName, style = MaterialTheme.typography.bodySmall)
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text("Scheduled on", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(appointment.schedule.toString(), style = MaterialTheme.typography.bodySmall)
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text("Status", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(appointment.status.toString(), style = MaterialTheme.typography.bodySmall)
        }

        Box {
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Options")
            }
            DropdownAppointment(
                expanded = isExpanded,
                onDismissClick = { isExpanded = false },
                onDropClick = onDropClick,
                onDeleteClick = {
                    isExpanded = false
                    showDeleteDialog = true
                },
                onVerifyClick = {
                    // Example logic: Call ViewModel to update the status
                    viewModel.updateStatus(appointment.appointmentId, "Verified")
                }
            )
        }
    }
}

@Composable
fun DropdownAppointment(
    expanded: Boolean,
    onDismissClick: () -> Unit,
    onDropClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onVerifyClick: () -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissClick
    ) {
        DropdownMenuItem(text = { Text("View") }, onClick = onDropClick)
        DropdownMenuItem(text = { Text("Delete") }, onClick = onDeleteClick)
        DropdownMenuItem(text = { Text("Verify") }, onClick = onVerifyClick)
    }
}
