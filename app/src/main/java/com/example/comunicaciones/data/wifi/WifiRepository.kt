package com.example.comunicaciones.data.wifi

import android.util.Log

class WifiRepository {

    private val api = WifiService()
    suspend fun doWifiIP(): String? {
        Log.i("ip2","Hasta ahora se trajo ${api.doWifiIP()}")
       return api.doWifiIP()
    }


}