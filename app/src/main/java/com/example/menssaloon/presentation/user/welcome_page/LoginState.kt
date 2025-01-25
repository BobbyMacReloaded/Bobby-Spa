package com.example.menssaloon.presentation.user.welcome_page

data class LoginState(
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val errorMessage: String = "",
    val isAdmin : Boolean = false,
    val isLoggedIn : Boolean = false,
    val error:String = ""
)