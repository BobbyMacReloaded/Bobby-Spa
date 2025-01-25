package com.example.menssaloon.presentation.admin.barber_screen

sealed class AddEditBarberEvent {
    data class EnteredName(val name:String):AddEditBarberEvent()
    data class EnteredPhoneNumber(val phoneNumber:String):AddEditBarberEvent()
    data object SaveBarber:AddEditBarberEvent()
}