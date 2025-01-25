package com.example.menssaloon.di

import com.example.menssaloon.data.repositoryImpl.AppointmentRepositoryImpl
import com.example.menssaloon.data.repositoryImpl.BarberRepositoryImpl
import com.example.menssaloon.data.repositoryImpl.ServiceRepositoryImpl
import com.example.menssaloon.domain.repository.AppointmentRepository
import com.example.menssaloon.domain.repository.BarberRepository
import com.example.menssaloon.domain.repository.ServiceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindAppointmentRepository(
        impl: AppointmentRepositoryImpl
    ):AppointmentRepository
    @Singleton
    @Binds
    abstract fun bindBarberRepository(
        impl: BarberRepositoryImpl
    ): BarberRepository
    @Singleton
    @Binds
    abstract fun bindServiceRepository(
        impl: ServiceRepositoryImpl
    ): ServiceRepository
}