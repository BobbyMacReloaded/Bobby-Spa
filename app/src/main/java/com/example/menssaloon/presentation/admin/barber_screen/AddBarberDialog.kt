@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.menssaloon.presentation.admin.barber_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.menssaloon.presentation.admin.dashboard.DashboardEvent
import com.example.menssaloon.presentation.admin.dashboard.DashboardViewmodel
import com.example.menssaloon.presentation.theme.YellowDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBarberDialog(
    showAddBarberDialog: Boolean,
    onDismissClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEditBarberViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewmodel = hiltViewModel()
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
   val state= viewModel.state.value

    if (showAddBarberDialog) {
        BasicAlertDialog(onDismissRequest = { onDismissClick() }) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                OutlinedTextField(
                    value = state.name,
                    label = {
                        Text("Barber's name")
                    },
                    onValueChange = {  viewModel.onEvent(AddEditBarberEvent.EnteredName(it)) },
                    modifier = modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    )
                )
                OutlinedTextField(
                    value = state.phoneNumber,
                    label = {
                        Text("Barber's phone Number")
                    },
                    onValueChange = {
                        viewModel.onEvent(AddEditBarberEvent.EnteredPhoneNumber(it))
                    },
                    modifier = modifier
                        .fillMaxWidth(),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
                Button(
                    onClick = {
                        viewModel.onEvent(AddEditBarberEvent.SaveBarber)
                        dashboardViewModel.onEvent(DashboardEvent.IncrementBarbersCount)
                        onConfirmClick() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = YellowDark)
                ) {
                    Text("Save")
                }
            }
        }
    }
}