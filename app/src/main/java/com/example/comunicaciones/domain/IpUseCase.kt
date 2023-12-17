package com.example.comunicaciones.domain

import com.example.comunicaciones.data.wifi.WifiRepository

class IpUseCase {
    //Primero metemos el repositorio

    private val repository = WifiRepository()

    suspend operator fun invoke(): String?{
        return repository.doWifiIP()
    }
}