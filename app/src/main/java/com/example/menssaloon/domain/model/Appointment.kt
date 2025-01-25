package com.example.menssaloon.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("appointment_table")
data class Appointment(
    val dateCreated: Date?,
    val fullName:String,
    val phoneNumber:String,
    val email:String,
    val status:String,
    val schedule: Date?,
    val selectedServices: List<Service>, // List of selected services
    val totalCost: Double, // Total cost of selected services

    @PrimaryKey(autoGenerate = true)
    val appointmentId:Int
)
