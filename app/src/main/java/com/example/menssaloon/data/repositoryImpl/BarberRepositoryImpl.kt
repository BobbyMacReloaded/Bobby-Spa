package com.example.menssaloon.data.repositoryImpl

import com.example.menssaloon.data.local.BarberDao
import com.example.menssaloon.domain.model.Barber
import com.example.menssaloon.domain.repository.BarberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BarberRepositoryImpl @Inject constructor(
    private val barberDao: BarberDao
): BarberRepository {
    override suspend fun upsertBarber(barber: Barber) {
      barberDao.upsertBarber(barber)
    }

    override suspend fun deleteBarber(barber: Barber) {
        barberDao.deleteBarber(barber)
    }

    override suspend fun getBarberById(barberId: Int):Barber? {
      return barberDao.getBarberById(barberId)
    }

    override fun getAllBarbers(): Flow<List<Barber>> {
       return barberDao.getAllBarbers()
    }
}