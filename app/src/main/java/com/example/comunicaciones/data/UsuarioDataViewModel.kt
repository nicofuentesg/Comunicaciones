package com.example.comunicaciones.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UsuarioDataViewModel: ViewModel() {



    var id by mutableStateOf("")
    var dispositivoMarca by mutableStateOf("")
    var dispositivoModelo by mutableStateOf("")
    var lat by mutableStateOf(0.0)
    var long by mutableStateOf(0.0)
    var velocidadInternet by mutableStateOf(0)
    var proveedorInternet by mutableStateOf("")
    var distanciaAlaRed by mutableStateOf("")

}