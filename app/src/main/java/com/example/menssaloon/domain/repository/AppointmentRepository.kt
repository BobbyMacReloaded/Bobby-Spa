package com.example.menssaloon.domain.repository

import com.example.menssaloon.domain.model.Appointment
import kotlinx.coroutines.flow.Flow

interface AppointmentRepository {
    suspend fun upsertAppointment(appointment: Appointment)

    suspend fun deleteAppointment(appointment: Appointment)

    suspend fun getAppointmentById(appointmentId:Int): Appointment?
    fun getAllAppointments(): Flow<List<Appointment>>
}