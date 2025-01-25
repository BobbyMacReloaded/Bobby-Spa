package com.example.menssaloon.domain.repository

interface DashboardRepository {

    suspend fun getServiceCount(): Int

    suspend fun getBarberCount(): Int

    suspend fun getPendingRequestCount()
}
