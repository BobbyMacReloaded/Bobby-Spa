package com.example.menssaloon.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.menssaloon.domain.model.Appointment
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Upsert
    suspend fun upsertAppointment(appointment: Appointment)
    @Delete
    suspend fun deleteAppointment(appointment: Appointment)

    @Query("SELECT * FROM appointment_table WHERE appointmentId= :appointmentId")
    suspend fun getAppointmentById(appointmentId:Int):Appointment?
    @Query("SELECT * FROM appointment_table")
    fun getAllAppointments():Flow<List<Appointment>>
}