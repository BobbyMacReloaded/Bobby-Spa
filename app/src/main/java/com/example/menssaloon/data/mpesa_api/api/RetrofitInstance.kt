package com.example.menssaloon.data.mpesa_api.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit: Retrofit by lazy {
        // Setup a logging interceptor for debugging
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Add the logging interceptor to OkHttp client
            .build()

        Retrofit.Builder()
            .baseUrl("https://sandbox.safaricom.co.ke/") // Base URL for Mpesa API
            .addConverterFactory(GsonConverterFactory.create()) // JSON parser
            .client(client) // Add OkHttp client with logging
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
