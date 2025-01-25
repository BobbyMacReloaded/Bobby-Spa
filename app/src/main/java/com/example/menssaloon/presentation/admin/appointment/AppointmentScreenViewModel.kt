package com.example.menssaloon.presentation.admin.appointment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.menssaloon.domain.model.Appointment
import com.example.menssaloon.domain.repository.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentScreenViewModel @Inject constructor(
    private val repository: AppointmentRepository,
    private val savedStateHandle: SavedStateHandle
):ViewModel() {
    private val _appointmentList = MutableStateFlow<List<Appointment>>(emptyList())
    val appointmentList = _appointmentList.asStateFlow()
    private val _selectedAppointment = MutableStateFlow<Appointment?>(null)
    val selectedAppointment : StateFlow<Appointment?> = _selectedAppointment
    init {
        fetchAllAppointments()
    }
    private fun fetchAllAppointments(){
       viewModelScope.launch {
           repository.getAllAppointments().collect{
         _appointmentList.value = it
           }
       }
    }
    fun selectAppointment(appointmentId: Int){
        _selectedAppointment.value = _appointmentList.value.find {
            it.appointmentId == appointmentId

        }
    }
    fun updateStatus(appointmentId: Int, newStatus: String){
      _appointmentList.value = _appointmentList.value.map {appointment->
          if (appointment.appointmentId == appointmentId){
             appointment.copy(status = newStatus)
          }else{
            appointment

      }

        }
        if (_selectedAppointment.value?.appointmentId == appointmentId){
            _selectedAppointment.value = _selectedAppointment.value?.copy(status = newStatus)
        }
    }
    fun deleteAppointment(appointment: Appointment){
        viewModelScope.launch {
            repository.deleteAppointment(appointment)
        }

    }


}