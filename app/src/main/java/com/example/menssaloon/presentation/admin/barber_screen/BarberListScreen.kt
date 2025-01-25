@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.menssaloon.presentation.admin.barber_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.menssaloon.R
import com.example.menssaloon.domain.model.Barber
import com.example.menssaloon.presentation.admin.appointment.AppointmentDeleteDialog
import com.example.menssaloon.presentation.admin.dashboard.DashboardEvent
import com.example.menssaloon.presentation.admin.dashboard.DashboardViewmodel
import com.example.menssaloon.presentation.theme.BlueDark
import kotlin.random.Random

@Composable
fun BarberListScreen(
    onBackClick:()->Unit,

    modifier: Modifier = Modifier,
    viewModel: BarberListScreenViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewmodel = hiltViewModel()
) {
    // List of available avatar resources
    val avatars = listOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar4,
    )

    // Initialize barber list with random avatars
    val barberList by viewModel.barberList.collectAsState()
    var showAddDialog by remember {
        mutableStateOf(false)
    }

    AddBarberDialog(
        onConfirmClick = {showAddDialog = false},
        onDismissClick = {showAddDialog = false},
        showAddBarberDialog = showAddDialog,

    )


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(

               onClick = {showAddDialog = true}) {
                Icon(Icons.Default.Add,contentDescription = null)
            }
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray
                ),
                title = {
                    Text(
                        "Barber List",
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {onBackClick()}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BlueDark)
        ) {
            if (barberList.isEmpty()) {
                Text(
                    text = "No barbers available\n Click on + to add a new barber",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }else {
                LazyColumn {
                    items(barberList) { barber ->
                        BarberItem(
                            barber = barber,
                            onDeleteClick = {
                                viewModel.deleteBarber(barber)
                                dashboardViewModel.onEvent(DashboardEvent.DecrementBarbersCount)
                            },
                            onEditClick = {
                                showAddDialog = true
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BarberItem(
    barber: Barber,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,

) {
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    if (showDeleteDialog){
        DeleteBarberDialog(
            onDismissClick = {
               showDeleteDialog = false
            },
            onConfirmClick = {
                showDeleteDialog = false
                onDeleteClick()}
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Avatar image
        Image(
            painter = painterResource(id = barber.avatar),
            contentDescription = "Avatar for ${barber.name}",
            modifier = Modifier
                .size(64.dp)
                .padding(end = 16.dp)
        )
        Spacer(modifier.width(20.dp))
        // Barber name
        Column(modifier.padding(6.dp)) {
            Text(
                text = barber.name,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier.width(20.dp))
        Text(text = if (barber.active) "Active" else "Inactive",
            color = Color.White)
        IconButton(onClick = {onEditClick()}) {
            Icon(Icons.Default.Edit,contentDescription = null)
        }
        IconButton(onClick = {
        showDeleteDialog = true}) {
            Icon(Icons.Default.Delete,contentDescription = null)
        }
    }

}

// Function to assign a random avatar to a Barber
fun assignRandomAvatar(
active:Boolean,
    name: String, id: Int, avatars: List<Int>, phoneNumber:String
): Barber {
    val randomAvatar = avatars[Random.nextInt(avatars.size)]
    return Barber(
        name = name,
        barberId = id,
        avatar = randomAvatar,
        active =active,
        number = phoneNumber
    )
}

@Preview
@Composable
private fun BarberPrev() {
    BarberListScreen(
        onBackClick = {},
       
    )
}
