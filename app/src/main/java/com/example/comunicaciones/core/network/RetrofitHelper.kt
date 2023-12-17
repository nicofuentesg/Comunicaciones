package com.example.comunicaciones.core.network

import com.example.comunicaciones.Auxiliares.probando.BASE_URL_INFO_IP
import com.example.comunicaciones.Auxiliares.probando.BASE_URL_IP
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {
    //Utilizamos una funcion para obtener el Gson y devolver un objeto retrofit
    fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder().baseUrl(BASE_URL_IP).addConverterFactory(
            GsonConverterFactory.create(gson)
        ).build()
    }

    //Utilizamos la funcion para crear un gson y devolver un objeto retrofit para obtener los datos sobre la ip

    /*
    val servicio = retrofit.create(MiInterfaz::class.java)

val ip = "tu_ip_aqui"
val respuesta = servicio.obtenerDatos(ip)
     */
    fun getIPRetrofit(): Retrofit {
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder().baseUrl(BASE_URL_INFO_IP)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }
}