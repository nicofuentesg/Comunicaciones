package com.example.comunicaciones.data.wifi

import com.example.comunicaciones.data.response.WifiResponse
import retrofit2.Response
import retrofit2.http.GET

/*

 */
interface WifiClient {

    //Aqui tendria que poner el IP de cada celular publico
    @GET("/?format=json")
    suspend fun doGetIP(): Response<WifiResponse>
}