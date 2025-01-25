package com.example.menssaloon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.menssaloon.domain.model.Appointment
import com.example.menssaloon.domain.model.Barber
import com.example.menssaloon.domain.model.Service

@Database(
    entities = [Appointment::class,Barber::class,Service::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SaloonDatabase:RoomDatabase() {
    abstract fun appointmentDao (): AppointmentDao
    abstract fun barberDao (): BarberDao
    abstract fun serviceDao (): ServiceDao

}