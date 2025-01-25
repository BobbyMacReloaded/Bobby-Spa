package com.example.menssaloon.presentation.admin.barber_screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DeleteBarberDialog(
    modifier: Modifier = Modifier,
    onConfirmClick:()->Unit,
    onDismissClick: () -> Unit,
    ) {

        AlertDialog(onDismissRequest = { onDismissClick() },
            title = { Text("Delete Barber") },
            text = { Text("Are you sure you want to delete this barber?\n This action cant be undone") },
            dismissButton ={
                TextButton({ onDismissClick() }) {
                    Text("Cancel")
                }
            },
            confirmButton = {
                TextButton({ onConfirmClick() }) {
                    Text("Delete")
                }
            },
        )
    }
