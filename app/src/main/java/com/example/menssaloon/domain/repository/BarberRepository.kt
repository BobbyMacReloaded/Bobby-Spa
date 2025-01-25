package com.example.menssaloon.domain.repository

import com.example.menssaloon.domain.model.Barber
import kotlinx.coroutines.flow.Flow

interface BarberRepository {
    suspend fun upsertBarber(barber: Barber)
    suspend fun deleteBarber(barber: Barber)
    suspend fun getBarberById(barberId: Int):Barber?
    fun getAllBarbers(): Flow<List<Barber>>
}