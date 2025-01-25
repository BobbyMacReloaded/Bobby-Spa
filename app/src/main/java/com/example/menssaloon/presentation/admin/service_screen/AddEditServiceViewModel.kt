package com.example.menssaloon.presentation.admin.service_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.menssaloon.domain.model.Service
import com.example.menssaloon.domain.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditServiceViewModel @Inject constructor(
    private val repository: ServiceRepository
):ViewModel() {
    var state by mutableStateOf(AddEditServiceState())
    private val _eventFlow = MutableSharedFlow<UIEvent>()

    val eventFlow = _eventFlow.asSharedFlow()
    private var currentServiceId: Int? = null
    fun onEvent(event: AddEditServiceEvent){
        when(event){
            is AddEditServiceEvent.EnteredCost -> {
                state = state.copy(serviceCost = event.cost)
            }
            is AddEditServiceEvent.EnteredDescription -> {
                state = state.copy(serviceDescription = event.description)
            }
            is AddEditServiceEvent.EnteredServiceName -> {
                state = state.copy(serviceName = event.name)
            }
            AddEditServiceEvent.SaveService -> saveService()
        }
    }

    private fun saveService() {
        viewModelScope.launch {
            when{
                state.serviceName.isBlank() -> {
                    viewModelScope.launch { _eventFlow.emit(UIEvent.ShowToast("Please enter the service name")) }
                }
                state.serviceCost.isBlank() -> {
                    viewModelScope.launch { _eventFlow.emit(UIEvent.ShowToast("Please enter the service cost")) }
                }
                state.serviceDescription.isBlank() -> {
                    viewModelScope.launch { _eventFlow.emit(UIEvent.ShowToast("Please enter the service description")) }
                }
            }
            try {
              repository.upsertService(
                  Service(
                      name = state.serviceName,
                      cost = state.serviceCost,
                      status = state.status,
                      serviceId = currentServiceId ?: 0,
                      description = state.serviceDescription,
                      isExpanded = state.isExpanded,
                     number = state.number
                  )
              )
                _eventFlow.emit(UIEvent.ShowToast("Successfully created a service"))
            }catch (e:Exception){
                _eventFlow.emit(UIEvent.ShowToast("Error creating service: ${e.message}"))
            }
        }
    }
}
sealed class UIEvent {
    data class ShowToast(val message: String) : UIEvent()
}
