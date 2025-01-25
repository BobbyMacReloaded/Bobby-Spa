package com.example.menssaloon.presentation.admin.barber_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.menssaloon.R
import com.example.menssaloon.domain.model.Barber
import com.example.menssaloon.domain.repository.BarberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditBarberViewModel @Inject constructor(
    private val repository: BarberRepository
):ViewModel() {
    private val _state = mutableStateOf(AddEditBarberState())
    val state:State<AddEditBarberState> get() = _state
    private val avatars = listOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar4
    )

    private val randomAvatarResId = avatars.random()
    private val currentBarberId: Int? = null
    fun onEvent(event: AddEditBarberEvent){
        when(event){
            is AddEditBarberEvent.EnteredName -> {
                _state.value = _state.value.copy(name = event.name)
            }
            is AddEditBarberEvent.EnteredPhoneNumber -> {
                _state.value = _state.value.copy(phoneNumber = event.phoneNumber)
            }

            AddEditBarberEvent.SaveBarber -> saveBarber()
        }
    }

    private fun saveBarber() {
        when {
            state.value.name.isBlank() -> {
                return
            }

            state.value.phoneNumber.isBlank() -> {
                return
            }
        }
       viewModelScope.launch {
           try {
         repository.upsertBarber(
             Barber(
                 name = state.value.name,
                 number = state.value.phoneNumber,
                 avatar = randomAvatarResId,
                 active = true,
                 barberId = currentBarberId ?:0
             )
         )
           }catch (e:Exception){
               e.printStackTrace()

           }
       }
    }
}