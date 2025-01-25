package com.example.menssaloon.presentation.admin.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.menssaloon.domain.model.Appointment
import com.example.menssaloon.domain.model.Barber
import com.example.menssaloon.domain.model.Service
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewmodel @Inject constructor():ViewModel() {
  private  val _uiState  = MutableStateFlow(DashboardState())
    val state :StateFlow<DashboardState> = _uiState
    fun onEvent(event: DashboardEvent){
        when(event){
            DashboardEvent.DecrementBarbersCount -> {
                _uiState.value = _uiState.value.copy(
                    barbersAvailableCount =  _uiState.value.barbersAvailableCount -1
                )
            }
            DashboardEvent.DecrementPendingRequests -> {
                _uiState.value = _uiState.value.copy(
                    pendingRequestsCount =  _uiState.value.pendingRequestsCount -1
                )
            }
            DashboardEvent.DecrementServiceCount -> {
                _uiState.value = _uiState.value.copy(
                    allServicesCount =  _uiState.value.allServicesCount -1
                )
            }
            DashboardEvent.IncrementBarbersCount -> {
                _uiState.value = _uiState.value.copy(
                    barbersAvailableCount =  _uiState.value.barbersAvailableCount +1
                )
            }
            DashboardEvent.IncrementPendingRequests -> {
                _uiState.value = _uiState.value.copy(
                    pendingRequestsCount =  _uiState.value.pendingRequestsCount +1
                )
            }
            DashboardEvent.IncrementServiceCount -> {
                _uiState.value = _uiState.value.copy(
                    allServicesCount =  _uiState.value.allServicesCount +1
                )
            }
        }
    }

    private fun updateCount(barberCount:Int, serviceCount:Int, pendingRequestCount:Int){
        _uiState.value = _uiState.value.copy(
            barbersAvailableCount = barberCount,
            allServicesCount = serviceCount,
            pendingRequestsCount = pendingRequestCount
        )
    }
    fun fetchData(barbersList: List<Barber>, servicesList: List<Service>, pendingRequestsList: List<Appointment>) {
        viewModelScope.launch {
            val barberCount = barbersList.size
            val serviceCount = servicesList.size
            val pendingRequestCount = pendingRequestsList.size

            // Update the UI state with the fetched data
            updateCount(barberCount, serviceCount, pendingRequestCount)
        }
    }
}