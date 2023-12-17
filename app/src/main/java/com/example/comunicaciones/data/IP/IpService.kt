package com.example.comunicaciones.data.IP

import com.example.comunicaciones.core.network.RetrofitHelper
import com.example.comunicaciones.data.response.IpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IpService {
    val retrofit = RetrofitHelper.getIPRetrofit()

    suspend fun doIPInfo(ip:String):IpResponse?{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(IPClient::class.java).doInfoIP(ip)
            response.body()
        }
    }
}