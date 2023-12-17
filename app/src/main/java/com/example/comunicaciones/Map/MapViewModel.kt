package com.example.comunicaciones.Map

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient

class MapViewModel: ViewModel() {
    val latitude = mutableStateOf(0.0)
    val longitude = mutableStateOf(0.0)

}