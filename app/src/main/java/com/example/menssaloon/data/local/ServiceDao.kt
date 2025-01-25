package com.example.menssaloon.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.menssaloon.domain.model.Appointment
import com.example.menssaloon.domain.model.Service
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Upsert
    suspend fun upsertService(service: Service)
    @Delete
    suspend fun deleteService(service: Service)

    @Query("SELECT * FROM service_table WHERE serviceId= :serviceId")
    suspend fun getServiceById(serviceId:Int): Service?
    @Query("SELECT * FROM service_table")
    fun getAllServices(): Flow<List<Service>>
}