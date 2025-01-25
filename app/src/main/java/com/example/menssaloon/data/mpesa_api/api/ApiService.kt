package com.example.menssaloon.data.mpesa_api.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // Endpoint to generate the access token
    @GET("oauth/v1/generate")
    suspend fun generateAccessToken(
        @Query("grant_type") grantType: String = "client_credentials",
        @Header("Authorization") authHeader: String
    ): AccessTokenResponse

    // Endpoint to initiate STK Push
    @POST("mpesa/stkpush/v1/processrequest")  // Corrected URL
    suspend fun initiateStkPush(
        @Header("Authorization") authHeader: String,
        @Body stkPushRequest: StkPushRequest
    ): ApiResponse
}
