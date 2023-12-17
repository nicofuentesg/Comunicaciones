package com.example.comunicaciones.data.IP

import com.example.comunicaciones.data.response.IpResponse

class IpRepository {
    val api = IpService()
    /*
       val ip = IpInfo().obtenerIP()
     */

    suspend fun doIpInfo(ip:String):IpResponse?{
        return api.doIPInfo(ip)
    }
}