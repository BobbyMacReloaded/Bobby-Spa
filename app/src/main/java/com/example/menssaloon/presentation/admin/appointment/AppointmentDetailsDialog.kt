@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.menssaloon.presentation.admin.appointment

import com.example.menssaloon.data.mpesa_api.api.MpesaViewModel
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.menssaloon.data.mpesa_api.api.StkPushResult
import com.example.menssaloon.domain.model.Appointment

@Composable
fun AppointmentDetailsDialog(
    showDetailsDialog: Boolean,
    appointment: Appointment?,
    onDismissRequest: () -> Unit,
    onVerifyClick: (Int, String) -> Unit,
    viewModel: MpesaViewModel
) {
    val context = LocalContext.current
    if (showDetailsDialog && appointment != null) {
       AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(text = "Appointment Details", fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    Text("Full Name: ${appointment.fullName}")
                    Text("Scheduled On: ${appointment.schedule}")
                    Text("Status: ${appointment.status}")
                    Text(appointment.phoneNumber)
                    Text("Total Cost:  Ksh ${appointment.totalCost}")
                }
            },
            confirmButton = {
                // Verify Button
               TextButton(
                    onClick = {
                        viewModel.initiateStkPush(
                            phoneNumber = appointment.phoneNumber,

                            onResult = { result ->
                                when (result) {
                                    is StkPushResult.Success -> {
                                        // Handle success: Show a success message and mark as verified
                                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT)
                                            .show()
                                        onVerifyClick(appointment.appointmentId, "Verified")
                                    }

                                    is StkPushResult.Error -> {
                                        // Handle error: Show the error message
                                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT)
                                            .show()
                                    }

                                    else -> {}
                                }
                            },
                            amount = appointment.totalCost
                        ) }
                ) {
                    Text("Verify")
                }
            },
            dismissButton = {
                // Not Verified Button
               TextButton(
                    onClick = { onVerifyClick(appointment.appointmentId, "Unverified") }
                ) {
                    Text("Not Verified")
                }
            }
        )
    }
}

