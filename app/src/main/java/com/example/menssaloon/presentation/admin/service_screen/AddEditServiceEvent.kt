package com.example.menssaloon.presentation.admin.service_screen

sealed class AddEditServiceEvent {
    data class EnteredCost(val cost:String):AddEditServiceEvent()
    data class EnteredServiceName(val name:String):AddEditServiceEvent()
    data class EnteredDescription(val description:String):AddEditServiceEvent()
    data object SaveService:AddEditServiceEvent()
}