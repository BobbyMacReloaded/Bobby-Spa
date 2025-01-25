
package com.example.menssaloon.presentation.user.welcome_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.menssaloon.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

// Define a data class for login states


@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    suspend fun login(username: String, password: String) {
        // Simulate authentication (replace with actual authentication logic)
        viewModelScope.launch {
            val user = authenticateUser(username, password)

             if (user != null && user.isAdmin) {
                _loginState.value = LoginState(isAdmin = true, isLoggedIn = true)

            } else {
                _loginState.value =
                    LoginState(isAdmin = false, isLoggedIn = false, error = "Access Denied")

            }
        }
    }

    private fun authenticateUser(username: String, password: String): User? {
        // Replace with actual user retrieval logic
        val users = listOf(
            User("admin", "password123", isAdmin = true),
            User("user", "password123", isAdmin = false)
        )
        return users.find { it.username == username && it.password == password }
    }
}



