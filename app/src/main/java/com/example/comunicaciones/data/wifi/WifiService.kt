package com.example.comunicaciones.data.wifi

import com.example.comunicaciones.core.network.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WifiService {
    /*
         val ip = IpInfo().obtenerIP()
         val retrofit = Retrofit.Builder()
    .baseUrl("https://tu.base.url")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val servicio = retrofit.create(MiInterfaz::class.java)

val ip = "tu_ip_aqui"
val respuesta = servicio.obtenerDatos(ip)
     */
    val retrofit = RetrofitHelper.getRetrofit()
    suspend fun doWifiIP():String?{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(WifiClient::class.java).doGetIP()
            response.body()?.IP
        }
    }
}