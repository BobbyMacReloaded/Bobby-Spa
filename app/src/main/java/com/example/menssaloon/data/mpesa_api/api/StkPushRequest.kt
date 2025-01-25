package com.example.menssaloon.data.mpesa_api.api

data class StkPushRequest(
    val BusinessShortCode: String, // Mpesa Paybill or Till number
    val Password: String, // Encoded password (explained later)
    val Timestamp: String, // Timestamp in 'yyyyMMddHHmmss' format
    val TransactionType: String, // Always "CustomerPayBillOnline"
    val Amount: Double, // The amount to pay
    val PartyA: String, // The customer's phone number
    val PartyB: String, // Business shortcode (same as BusinessShortCode)
    val PhoneNumber: String, // The customer's phone number
    val CallBackURL: String, // Your server URL to receive payment updates
    val AccountReference: String, // Payment reference (like invoice number)
    val TransactionDesc: String // Description (e.g., "Payment for services")
)