package com.example.menssaloon.presentation.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.menssaloon.R
import com.example.menssaloon.presentation.theme.BlueDark
import com.example.menssaloon.presentation.theme.YellowDark
import com.example.menssaloon.presentation.user.welcome_page.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginDialog(
    isOpen: Boolean,
    onLoginSuccess: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val loginState by loginViewModel.loginState.collectAsState()

    if (isOpen) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }

        // Check for errors in login state
        LaunchedEffect(loginState) {
            errorMessage = loginState.error ?: ""
        }

        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            title = { Text("Login") },
            text = {
                Column {
                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") }
                    )
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation()
                    )
                    // Display error message if login failed
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }else{
                        Text(
                            text = "Password cant be empty",
                            color = Color.Green,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            loginViewModel.login(username, password)

                            // Check if the login was successful
                            if (loginState.isLoggedIn) {
                                onLoginSuccess()
                            }
                        }
                    }
                ) {
                    Text("Login")
                }
            },
            dismissButton = {
                Button(onClick = { onDismissRequest() }) {
                    Text("Cancel")
                }
            }
        )
    }
}




// Preview function to see the layout in the preview window
