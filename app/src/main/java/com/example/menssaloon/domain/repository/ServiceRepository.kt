package com.example.menssaloon.domain.repository

import com.example.menssaloon.domain.model.Service
import kotlinx.coroutines.flow.Flow

interface ServiceRepository{

    suspend fun upsertService(service: Service)

    suspend fun deleteService(service: Service)

    suspend fun getServiceById(serviceId:Int): Service?
    fun getAllServices(): Flow<List<Service>>
}