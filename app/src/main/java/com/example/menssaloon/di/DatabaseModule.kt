package com.example.menssaloon.di

import android.app.Application
import androidx.room.Room
import com.example.menssaloon.data.local.AppointmentDao
import com.example.menssaloon.data.local.BarberDao
import com.example.menssaloon.data.local.SaloonDatabase
import com.example.menssaloon.data.local.ServiceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
    ):SaloonDatabase{
        return Room
            .databaseBuilder(
                application,
                SaloonDatabase::class.java,
                "saloon_db"
            )
            .build()
    }
    @Provides
    @Singleton
    fun provideAppointmentDao(database: SaloonDatabase):AppointmentDao{
        return database.appointmentDao()
    }
    @Provides
    @Singleton
    fun provideBarberDao(database: SaloonDatabase): BarberDao {
        return database.barberDao()
    }
    @Provides
    @Singleton
    fun provideServiceDao(database: SaloonDatabase): ServiceDao{
        return database.serviceDao()
    }
}