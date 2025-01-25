package com.example.menssaloon.presentation.admin.service_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.menssaloon.domain.model.Service
import com.example.menssaloon.domain.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceListViewModel @Inject constructor(
    private val repository: ServiceRepository
):ViewModel() {
    private val _serviceList = MutableStateFlow<List<Service>>(emptyList())
    val serviceList = _serviceList.asStateFlow()
    init {
fetchAllServices()
    }
    private fun fetchAllServices(){
        viewModelScope.launch {
            repository.getAllServices().collect {
                _serviceList.value = it
            }

        }
    }
    fun updateService(updatedService: Service) {
        viewModelScope.launch {
            _serviceList.value = _serviceList.value.map {
                if (it.serviceId == updatedService.serviceId) updatedService else it
            }
        }
    }
fun deleteService(service: Service){
    viewModelScope.launch {
        repository.deleteService(service)
    }

}
}