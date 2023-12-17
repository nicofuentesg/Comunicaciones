package com.example.comunicaciones.data.response

import com.google.gson.annotations.SerializedName

/*
"status": "success",
  "isp": "NSS S.A.",
 */
data class IpResponse(@SerializedName("status") val status: String, @SerializedName("isp") val isp: String)