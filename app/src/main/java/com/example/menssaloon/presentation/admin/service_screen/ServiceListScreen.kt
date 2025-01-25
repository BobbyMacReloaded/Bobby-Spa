package com.example.menssaloon.presentation.admin.service_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.menssaloon.domain.model.Service
import com.example.menssaloon.presentation.admin.dashboard.DashboardEvent
import com.example.menssaloon.presentation.admin.dashboard.DashboardViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceListScreen(
    onBackClick: () -> Unit,
    viewModel: ServiceListViewModel = hiltViewModel()
) {
    val services by viewModel.serviceList.collectAsState()
    var showAddEditDialog by remember { mutableStateOf(false) }

    if (showAddEditDialog) {
        AddEditService(
            showAddEditDialogDialog = showAddEditDialog,
            onDismissClick = { showAddEditDialog = false },
            onConfirmClick = { showAddEditDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Services",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddEditDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Service")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            ServiceList(
                services = services,
                onExpanded = {updatedService ->
                    viewModel.updateService(updatedService)},

            )
        }
    }
}

@Composable
private fun ServiceItem(
    service: Service,
    onExpanded: (Service) -> Unit,
    onDeleteClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        DeleteServiceDialog(
            onDismissClick = { showDeleteDialog = false },
            onConfirmClick = {
                onDeleteClick()
                showDeleteDialog = false
            }
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)
                .background(
                    if (isExpanded) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surface
                )
                .clickable {
                    isExpanded = !isExpanded
                    onExpanded(service.copy(isExpanded = isExpanded))
                }
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = service.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Icon(
                    imageVector = if (isExpanded) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Price: $${service.cost}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = service.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
        IconButton(onClick = { showDeleteDialog = true }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun ServiceList(
    services: List<Service>,
    viewModel: ServiceListViewModel = hiltViewModel(),
   dashboardViewmodel: DashboardViewmodel = hiltViewModel(),
    onExpanded: (Service) -> Unit, // Explicit callback
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(services) { service ->
            ServiceItem(
                service,
                onExpanded = onExpanded, // Pass the callback
                onDeleteClick = {
                    // Handle delete inside ServiceItem
                viewModel.deleteService(service)
                    dashboardViewmodel.onEvent(DashboardEvent.DecrementServiceCount)
                }
            )
        }
    }
}


