package com.example.menssaloon.presentation.admin.service_screen

data class AddEditServiceState(
    val serviceName:String= "",
    val serviceCost:String= "",
    val serviceDescription:String= "",
    val serviceId:Int?= null,
    val status:String = "",
    var isExpanded: Boolean = false,
    val number: Int = 0
)
