package com.example.menssaloon.presentation.user.booking_screen

import com.example.menssaloon.domain.model.Service
import java.util.Date

data class BookingScreenStates(
    val fullName: String = "",
    val email: String = "",
    val status: String ="",
    val schedule: Date? = null,
    val phoneNumber: String = "",
    val dateCreated: Date? = null,
    val appointmentId: Int? = null,
    val selectedServices: List<Service> = emptyList(),
    var totalCost: Double = 0.0
)