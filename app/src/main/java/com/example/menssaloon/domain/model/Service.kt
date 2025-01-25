package com.example.menssaloon.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity("service_table")
data class Service(

    val name:String,
    val description:String,
    val cost: String,
    val status:String,
    val number :Int,
    var isExpanded: Boolean ,
    @PrimaryKey(autoGenerate = true)
    val serviceId:Int,
)