package com.example.menssaloon.data.repositoryImpl

import com.example.menssaloon.data.local.ServiceDao
import com.example.menssaloon.domain.model.Service
import com.example.menssaloon.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val serviceDao: ServiceDao
):ServiceRepository {
    override suspend fun upsertService(service: Service) {
       serviceDao.upsertService(service)
    }

    override suspend fun deleteService(service: Service) {
      serviceDao.deleteService(service)
    }

    override suspend fun getServiceById(serviceId: Int): Service? {
        return  serviceDao.getServiceById(serviceId)
    }

    override fun getAllServices(): Flow<List<Service>> {
       return  serviceDao.getAllServices()
    }
}