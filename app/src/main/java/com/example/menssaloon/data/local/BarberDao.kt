package com.example.menssaloon.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.menssaloon.domain.model.Barber
import kotlinx.coroutines.flow.Flow

@Dao
interface BarberDao {
    @Upsert
    suspend fun upsertBarber(barber: Barber)
    @Delete
    suspend fun deleteBarber(barber: Barber)
    @Query("SELECT * FROM barber_table WHERE barberId = :barberId")
    suspend fun getBarberById(barberId: Int):Barber?
    @Query("SELECT * FROM barber_table")
    fun getAllBarbers():Flow<List<Barber>>
}