package com.example.menssaloon.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barber_table")
data class Barber(
    val name:String,
    val number:String,
    val avatar:Int,
    val active:Boolean,
    @PrimaryKey(autoGenerate = true)
    val barberId:Int,

)
