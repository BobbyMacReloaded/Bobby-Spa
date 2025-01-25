package com.example.menssaloon.presentation.admin.service_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.menssaloon.presentation.admin.dashboard.DashboardEvent
import com.example.menssaloon.presentation.admin.dashboard.DashboardViewmodel
import com.example.menssaloon.presentation.theme.YellowDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditService(
    showAddEditDialogDialog: Boolean,
    onDismissClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEditServiceViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewmodel = hiltViewModel()
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val state = viewModel.state

    LaunchedEffect(showAddEditDialogDialog) {
        if (showAddEditDialogDialog) {
            focusRequester.requestFocus()
        }
    }

    if (showAddEditDialogDialog) {
        BasicAlertDialog(
            onDismissRequest = { onDismissClick() }
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Add or Edit Service",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = state.serviceName,
                    onValueChange = {
                        viewModel.onEvent(AddEditServiceEvent.EnteredServiceName(it))
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
                OutlinedTextField(
                    value = state.serviceDescription,
                    onValueChange = { viewModel.onEvent(AddEditServiceEvent.EnteredDescription(it)) },
                    label = { Text("Service Description") },
                    modifier = modifier.fillMaxWidth(),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
                OutlinedTextField(
                    value = state.serviceCost,
                    onValueChange = { viewModel.onEvent(AddEditServiceEvent.EnteredCost(it) )},
                    label = { Text("Cost") },
                    modifier = modifier.fillMaxWidth(),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
                Button(
                    onClick = {
                        viewModel.onEvent(AddEditServiceEvent.SaveService)
                        onConfirmClick()
                              dashboardViewModel.onEvent(DashboardEvent.IncrementServiceCount)
                              },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = YellowDark)
                ) {
                    Text("Save")
                }
            }
        }
    }
}