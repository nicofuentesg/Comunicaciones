package com.example.comunicaciones.data.response

import com.google.gson.annotations.SerializedName

//Al devolver solamente la ip no tiene estrcutra json
data class WifiResponse(@SerializedName("ip") val IP:String)
