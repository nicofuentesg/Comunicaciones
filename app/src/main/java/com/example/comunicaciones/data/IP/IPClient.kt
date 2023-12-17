package com.example.comunicaciones.data.IP

import com.example.comunicaciones.data.response.IpResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IPClient {
   /*
   interface MiInterfaz {
    @GET("/{ip}/?format=json")
    suspend fun obtenerDatos(@Path("ip") ip: String): Call<TuObjetoDeRespuesta>
}
    */
    @GET("{ip}")
    suspend fun doInfoIP(@Path("ip") ip: String): Response<IpResponse>
}