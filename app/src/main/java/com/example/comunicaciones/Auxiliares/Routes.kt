package com.example.comunicaciones.Auxiliares

sealed class Routes(val route:String){
    object HomeLogin: Routes("home")
    object WifiInfoScreen: Routes("WifiInfo")
    object RedInfoScreen: Routes("RedInfo")
}