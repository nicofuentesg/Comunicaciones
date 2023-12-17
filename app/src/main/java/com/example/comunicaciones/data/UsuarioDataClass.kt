package com.example.comunicaciones.data

import java.util.UUID

data class UsuarioDataClass(
    val id: String = UUID.randomUUID().toString(),
    val dispositivoMarca: String,
    val dispositivoModelo: String,
    val lat: Double,
    val long: Double,
    val velocidadInternet: Int,
    val proveedorInternet: String,
    val distanciaAlaRed: String
){

    // Constructor sin argumentos requerido por Firestore
    constructor() : this(
        id = "",
        dispositivoMarca = "",
        dispositivoModelo = "",
        lat = 0.0,
        long = 0.0,
        velocidadInternet = 0,
        proveedorInternet = "",
        distanciaAlaRed = ""
    )

    fun toMap(): MutableMap<Any, Any>{
        return mutableMapOf(
            "user_id" to this.id,
            "dispotivo_marca" to this.dispositivoMarca,
            "dispostivo_modelo" to this.dispositivoModelo,
            "latitud" to this.lat,
            "longitud" to this.long,
            "velocidad_internet" to this.velocidadInternet,
            "proveedor_internet" to this.proveedorInternet,
            "distancia_a_la_red" to this.distanciaAlaRed
        )
    }


}

