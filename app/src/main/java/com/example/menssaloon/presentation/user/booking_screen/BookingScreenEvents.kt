package com.example.menssaloon.presentation.user.booking_screen

import com.example.menssaloon.domain.model.Service
import java.util.Date

sealed class BookingScreenEvents {
    data class EnteredName(val name: String) : BookingScreenEvents()
    data class EnteredEmail(val email: String) : BookingScreenEvents()
    data class EnteredPhoneNumber(val phoneNumber: String) : BookingScreenEvents()
    data class EnteredScheduledDate(val scheduledDate: Date) : BookingScreenEvents()
    object Submit : BookingScreenEvents()
    data class ToggleServiceSelection(val service: Service) : BookingScreenEvents()
    data class UpdateVerificationStatus(val isVerified: Boolean) : BookingScreenEvents()
}
