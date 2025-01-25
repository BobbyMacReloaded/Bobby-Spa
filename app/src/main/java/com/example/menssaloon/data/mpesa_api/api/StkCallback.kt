package com.example.menssaloon.data.mpesa_api.api

data class StkCallback(
    val callbackMetadata: CallbackMetadata,
    val checkoutRequestID: String,
    val merchantRequestID: String,
    val resultCode: Int,
    val resultDesc: String,
    val customerMessage: String
)