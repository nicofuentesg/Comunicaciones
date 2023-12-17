package com.example.comunicaciones.domain

import com.example.comunicaciones.data.IP.IpRepository
import com.example.comunicaciones.data.response.IpResponse

class IpInfoUseCaso {
    private val repository = IpRepository()

    suspend operator fun invoke(ip:String): IpResponse?{
        return repository.doIpInfo(ip)
    }

}