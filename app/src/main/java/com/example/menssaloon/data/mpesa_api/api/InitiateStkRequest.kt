package com.example.menssaloon.data.mpesa_api.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Base64
import android.util.Log
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*


class MpesaViewModel  : ViewModel() {

    // Function to generate access token
    private suspend fun getAccessToken(): String = withContext(Dispatchers.IO) {
        val authHeader = "Basic " + Base64.encodeToString(
            "$CONSUMER_KEY:$CONSUMER_SECRET".toByteArray(),
            Base64.NO_WRAP
        )
        val response = RetrofitInstance.apiService.generateAccessToken(authHeader = authHeader)
        response.access_token
    }

    // Function to initiate STK Push
    fun initiateStkPush(amount: Double, phoneNumber: String, onResult: (StkPushResult) -> Unit) {
        viewModelScope.launch {
            try {
                // Step 1: Get access token
                val accessToken = getAccessToken()

                Log.d("MpesaViewModel", "Access Token: $accessToken")

                // Step 2: Prepare the STK Push request
                val timestamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
                val password = Base64.encodeToString(
                    ("$BUSINESS_SHORT_CODE$PASS_KEY$timestamp").toByteArray(),
                    Base64.NO_WRAP
                )

                val stkPushRequest = StkPushRequest(
                    BusinessShortCode = BUSINESS_SHORT_CODE,
                    Password = password,
                    Timestamp = timestamp,
                    TransactionType = "CustomerPayBillOnline",
                    Amount = amount,
                    PartyA = phoneNumber,
                    PartyB = BUSINESS_SHORT_CODE,
                    PhoneNumber = phoneNumber,
                    CallBackURL = "https://9245-102-166-62-197.ngrok-free.app/callback",
                    AccountReference = "MensSalonApp",
                    TransactionDesc = "Payment for services"
                )

                // Step 3: Call the STK Push API
                val response = RetrofitInstance.apiService.initiateStkPush(
                    authHeader = "Bearer $accessToken",
                    stkPushRequest = stkPushRequest
                )
                Log.d("MpesaViewModel", "STK Push Response: $response")

                // Step 4: Handle the response
                if (response.ResponseCode == "0") {
                    onResult(StkPushResult.Success("STK Push initiated: ${response.CustomerMessage}"))
                } else {
                    onResult(StkPushResult.Error("Error: ${response.ResponseDescription}"))
                }
            } catch (e: HttpException) {
                // Log HTTP error details
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("MpesaViewModel", "HTTP 400 Error: $errorBody", e)
                onResult(StkPushResult.Error("HTTP Error: $errorBody"))
            } catch (e: Exception) {
                // Log general errors
                Log.e("MpesaViewModel", "Error: ${e.message}", e)
                onResult(StkPushResult.Error("Error: ${e.message}"))
            }
        }
    }

    companion object {
        const val CONSUMER_KEY = "NH4rStbKWynNixofYtU4hi1W9lnwMiC29NB0lCdL6AIWc8OW"
        const val CONSUMER_SECRET = "37myzvZrexFGcqhrAst2vcuCy4fRVjOWzxh9bMYvJfJWlYGoMRwFbOGmNuyPSSkM"
        const val BUSINESS_SHORT_CODE = "174379"
        const val PASS_KEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"
    }
}
sealed class StkPushResult {
    data class Success(val message: String) : StkPushResult()
    data class Error(val message: String) : StkPushResult()
}

