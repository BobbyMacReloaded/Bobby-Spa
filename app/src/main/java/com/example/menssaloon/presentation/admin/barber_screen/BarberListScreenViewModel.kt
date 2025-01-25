package com.example.menssaloon.presentation.admin.barber_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.menssaloon.domain.model.Barber
import com.example.menssaloon.domain.repository.BarberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BarberListScreenViewModel @Inject constructor(
    private val repository: BarberRepository
):ViewModel() {
    private val _barberList = MutableStateFlow<List<Barber>>(emptyList())
    val barberList = _barberList.asStateFlow()
    init {
        fetchBarberList()
    }
    private fun fetchBarberList(){
        viewModelScope.launch {
            repository.getAllBarbers().collect {
                _barberList.value = it
            }
        }
    }
    fun deleteBarber(barber: Barber){
        viewModelScope.launch {
            repository.deleteBarber(barber)
        }
    }
}