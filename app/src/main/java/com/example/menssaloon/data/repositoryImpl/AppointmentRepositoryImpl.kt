package com.example.menssaloon.data.repositoryImpl

import com.example.menssaloon.data.local.AppointmentDao
import com.example.menssaloon.domain.model.Appointment
import com.example.menssaloon.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppointmentRepositoryImpl @Inject constructor(
    private val appointmentDao: AppointmentDao
):AppointmentRepository {
    override suspend fun upsertAppointment(appointment: Appointment) {
       appointmentDao.upsertAppointment(appointment)
    }

    override suspend fun deleteAppointment(appointment: Appointment) {
      appointmentDao.deleteAppointment(appointment)
    }

    override suspend fun getAppointmentById(appointmentId: Int): Appointment? {
        return appointmentDao.getAppointmentById(appointmentId)
    }

    override fun getAllAppointments(): Flow<List<Appointment>> {
       return appointmentDao.getAllAppointments()
    }
}