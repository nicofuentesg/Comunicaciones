package com.example.comunicaciones.Wifi

import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comunicaciones.domain.IpInfoUseCaso

import com.example.comunicaciones.domain.IpUseCase
import kotlinx.coroutines.launch

class WifiInfoViewModel : ViewModel() {

    //Creamos y llamamos a nuestro primer caso de uso
    val IpUseCase = com.example.comunicaciones.domain.IpUseCase()
    val IpInfoUseCaso = IpInfoUseCaso()
    //variable para la ip
    var IpObtenidaPorJson: String = ""

    //Get and set
    //Utilizamos el wifiInfo para modificarlo internamente
    private val _wifiInfo = MutableLiveData<WifiInfo>()
    val wifiInfo: LiveData<WifiInfo> = _wifiInfo

    //Velocidad
    private val _wifiSpeed = MutableLiveData<Int>()
    val wifiSpeed: LiveData<Int> = _wifiSpeed

    //DistanciaRSSi
    private var _distanciaWifi = MutableLiveData<Int>()
    val distanciaWifi: LiveData<Int> = _distanciaWifi


    //Proveedor de internet
    private var _wifiProvider = MutableLiveData<String>()
    val wifiProvider :LiveData<String> = _wifiProvider

    //LoadingPantalla
    private var _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> = _loading

    fun inicializarWifiInfo(WifiManager: WifiManager) {
        val wifiInfo = WifiManager.connectionInfo
        _wifiInfo.value = wifiInfo
        getIP()
        obtenerVelocidad()
        obtenerDistancia()
    }

    //Para realizar bien la arquitectura este wifiInfo lo tendria que llamar desde la capa de modelo

    //Obtenemos la velocidad
  private  fun obtenerVelocidad() {
        val currentWifiInfo = wifiInfo.value
        if (currentWifiInfo != null) {
            _wifiSpeed.value = currentWifiInfo.linkSpeed
            Log.i("pero", "internet ${_wifiSpeed.value}")
        } else {
            Log.i("es un error", "apa")
        }
    }

    //Obtenemos el RSSI para medir la distancia
    private fun obtenerDistancia() {

        val currentWifiInfo = wifiInfo.value
        var rssi = 0
        val n = 2
        val ptx = -30
        if (currentWifiInfo != null) {
            rssi = currentWifiInfo.rssi
            _distanciaWifi.value = Math.pow(10.0, ((ptx - rssi) / (10 * n)).toDouble()).toInt()

            //Probamos
            IpUseCase
            Log.i("distancia", "${_distanciaWifi.value} en Metros")
        } else {
            Log.i("es un error", "apa")
        }
    }

    private fun getIP() {
        viewModelScope.launch {
            val result = IpUseCase.invoke()
            if (!result.isNullOrEmpty()) {
                //Se realizo bien el llamado y tenemos la ip
                Log.i("ip2", "El resultado es $result")
                 IpObtenidaPorJson = result
                obtenerDatosAdicionalesIP()
              //  probadita()
            } else {
                //se rompio

            }

        }
    }

    private fun obtenerDatosAdicionalesIP() {
        viewModelScope.launch {
            val result = IpInfoUseCaso.invoke(IpObtenidaPorJson)
            if (result != null){
                if (result.status == "success"){
                    //obtenemos el isp
                    //Logramos tener el objeto de invocacion
                    Log.i("resultado","${result.isp}")
                    _wifiProvider.value = result.isp
                    _loading.value = false
                }
            }
        }

    }



    //Le puedo mandar una clase con el IP y luego hago el llamado
    fun enviarIpObtenida(): String {
        Log.i("ip","Se envio la ip")
        return IpObtenidaPorJson
    }



}

