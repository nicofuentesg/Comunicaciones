package com.example.comunicaciones.data.IP

import com.example.comunicaciones.Wifi.WifiInfoViewModel

class IpInfo(private val wifiInfo: WifiInfoViewModel) {
    /*

     */
     fun obtenerIP():String{

        val ip = wifiInfo.enviarIpObtenida()
        return ip
    }
}