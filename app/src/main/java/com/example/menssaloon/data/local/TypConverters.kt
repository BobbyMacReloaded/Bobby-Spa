package com.example.menssaloon.data.local

import androidx.room.TypeConverter
import com.example.menssaloon.domain.model.Service
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Types
import java.util.*

class Converters {

    // Moshi initialization with KotlinJsonAdapterFactory
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    // Define the type for the list of Service
    private val type = Types.newParameterizedType(List::class.java, Service::class.java)

    // Create the Moshi adapter
    private val adapter = moshi.adapter<List<Service>>(type)

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(time: Long): Date {
        return Date(time)
    }

    @TypeConverter
    fun fromServiceList(services: List<Service>): String {
        return adapter.toJson(services)
    }

    @TypeConverter
    fun toServiceList(servicesJson: String): List<Service> {
        return adapter.fromJson(servicesJson) ?: emptyList()
    }
}
