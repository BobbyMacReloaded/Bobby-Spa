package com.example.menssaloon.data.mpesa_api

import kotlinx.coroutines.flow.StateFlow

sealed class NetworkResponse {
    data class Success<out T>(val data: T) : NetworkResponse()
    data class Error(val message: String) : NetworkResponse()
 data object  Loading : NetworkResponse()
}