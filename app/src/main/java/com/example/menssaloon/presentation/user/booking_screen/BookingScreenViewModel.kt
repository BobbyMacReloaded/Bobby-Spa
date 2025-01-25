package com.example.menssaloon.presentation.user.booking_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.menssaloon.data.mpesa_api.NetworkResponse
import com.example.menssaloon.domain.model.Appointment
import com.example.menssaloon.domain.repository.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class BookingScreenViewModel @Inject constructor(
    private val repository: AppointmentRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(BookingScreenStates())
    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    private var currentAppointmentId: Int? = null
    // Private MutableStateFlow to update the state internally
    private var _networkResponse = MutableStateFlow<NetworkResponse>(value = NetworkResponse.Loading)

    // Publicly exposed as StateFlow (read-only)
    var networkResponse: StateFlow<NetworkResponse> = _networkResponse.asStateFlow()


    init {
        getAppointmentId()
    }
    fun onEvent(event: BookingScreenEvents) {
        when (event) {
            is BookingScreenEvents.EnteredName -> {
                state = state.copy(fullName = event.name)
            }
            is BookingScreenEvents.EnteredEmail -> {
                state = state.copy(email = event.email)
            }
            is BookingScreenEvents.EnteredPhoneNumber -> {
                state = state.copy(phoneNumber = event.phoneNumber)
            }
            is BookingScreenEvents.EnteredScheduledDate -> {
                state = state.copy(schedule = event.scheduledDate)
            }
            is BookingScreenEvents.ToggleServiceSelection -> {
                val updatedServices = state.selectedServices.toMutableList()
                if (updatedServices.contains(event.service)) {
                    updatedServices.remove(event.service)
                    state = state.copy(
                        selectedServices = updatedServices,
                        totalCost = state.totalCost - event.service.cost.toDouble()
                    )
                } else {
                    updatedServices.add(event.service)
                    state = state.copy(
                        selectedServices = updatedServices,
                        totalCost = state.totalCost + event.service.cost.toDouble()
                    )
                }
            }
            BookingScreenEvents.Submit -> submitBooking()
            is BookingScreenEvents.UpdateVerificationStatus -> {
                state = state.copy(status = event.isVerified.toString())
            }
        }
    }

    private fun submitBooking() {
        viewModelScope.launch {
            val emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
            when {
                state.fullName.isBlank() -> {
                    _eventFlow.emit(UIEvent.ShowToast("Please enter your name"))
                    return@launch
                }
                !emailPattern.matcher(state.email).matches() -> {
                    _eventFlow.emit(UIEvent.ShowToast("Please enter a valid email"))
                    return@launch
                }
                state.phoneNumber.length < 10 || state.phoneNumber.any { !it.isDigit() } -> {
                    _eventFlow.emit(UIEvent.ShowToast("Please enter a valid phone number"))
                    return@launch
                }
                state.schedule == null -> {
                    _eventFlow.emit(UIEvent.ShowToast("Please select a scheduled date"))
                    return@launch
                }
                state.selectedServices.isEmpty() -> {
                    _eventFlow.emit(UIEvent.ShowToast("Please select at least one service"))
                    return@launch
                }
                else -> {
                    _networkResponse.value  =NetworkResponse.Loading
                    try {
                        repository.upsertAppointment(
                            Appointment(
                                dateCreated = state.dateCreated ?: Date(),
                                fullName = state.fullName,
                                phoneNumber = state.phoneNumber,
                                email = state.email,
                                schedule = state.schedule!!,
                                status = state.status.toString(),
                                selectedServices = state.selectedServices,
                                totalCost = state.totalCost,
                                appointmentId = currentAppointmentId ?: 0
                            )
                        )
                        _networkResponse.value = NetworkResponse.Success("Appointment booked successfully")
                        _eventFlow.emit(UIEvent.ShowToast("Appointment booked successfully"))
                    } catch (e: Exception) {
                        _networkResponse.value = NetworkResponse.Error("Error booking appointment: ${e.message}")
                        _eventFlow.emit(UIEvent.ShowToast("Error booking appointment: ${e.message}"))
                    }
                }
            }
        }
    }
    private fun getAppointmentId() {
        savedStateHandle.get<Int>("appointmentId")?.let {appointmentId ->
if (appointmentId != -1){
    viewModelScope.launch {
       repository.getAppointmentById(appointmentId)?.let { appointment ->
            state = state.copy(
               fullName = appointment.fullName,
                email = appointment.email,
                phoneNumber = appointment.phoneNumber,
                schedule =appointment.schedule,
                selectedServices =appointment.selectedServices,
                totalCost = appointment.totalCost,
                status = appointment.status

                )
            currentAppointmentId = appointmentId
        }
    }
}
        }

    }
}


sealed class UIEvent {
    data class ShowToast(val message: String) : UIEvent()
}
