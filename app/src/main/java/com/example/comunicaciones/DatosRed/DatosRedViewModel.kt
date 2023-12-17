package com.example.comunicaciones.DatosRed

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DatosRedViewModel(): ViewModel() {

    //LoadingPantalla
    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // Metodo para obtener la velocidad de la red movil.

    private val _velocidadRedMovil = MutableLiveData<Int>()
    val velocidadRedMovil: LiveData<Int> = _velocidadRedMovil

    //Datos Moviles
    private val _nombreDeProovedor = MutableLiveData<String>()
    val nombreDeProovedro: LiveData<String> = _nombreDeProovedor

    //Metodo para obtener la velocidad de la red movil

    fun obtenerVelocidadRedMovil(context: Context){
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork

        Log.i("network","la conexion es:$network")
        if (network != null){
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            val velocidadDatosMoviles = networkCapabilities!!.linkDownstreamBandwidthKbps
            val velocidadDatosMovilesMbps = velocidadDatosMoviles / 1000.0
            _velocidadRedMovil.postValue(velocidadDatosMovilesMbps.toInt())


            _velocidadRedMovil.value = velocidadDatosMoviles
        }else{
            Log.d("VelocidadDatosMoviles", "No hay una red activa")
        }
    }

    fun obtenerProovedorDatosMoviles(context: Context){
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val nombreProveedor = telephonyManager.networkOperatorName
        if (nombreProveedor.isNotEmpty()){
            Log.i("ProveedorDatosMoviles","Proveedor de datos moviles $nombreProveedor")
            _nombreDeProovedor.postValue(nombreProveedor)
            _loading.postValue(false)
        }else{
            Log.i("ProveedorDatosMoviles", "No se pudo obtener el proveedor de datos móviles")
        }
    }




}

/*
ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    //should check null because in airplane mode it will be null
    NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
    int downSpeed = nc.getLinkDownstreamBandwidthKbps();
    int upSpeed = nc.getLinkUpstreamBandwidthKbps();
 */