package com.example.comunicaciones.ui.theme

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comunicaciones.Networkwatches
import com.example.comunicaciones.R

/*
@Composable
fun MenuPrincipal() {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Bienvenido")
            },
            modifier = Modifier.background(Color.Blue)
        )
    }){
        //screen
        
    }
}*/



@Composable
fun GreeatingText(message: String, from: String, modifier: Modifier) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = TextStyle(
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                lineHeight = 116.sp
            )
        )
        Text(
            text = from,
            style = TextStyle(color = Color.White, fontSize = 16.sp),
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.End)
        )

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiPantalla() {

    Scaffold(topBar = { TopBar() }) {
        MiContenidoDePantalla()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "Informacion De los Datos") },
        modifier = Modifier.background(Color.Blue)
    )
}

@Composable
fun MiContenidoDePantalla() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp), contentAlignment = Alignment.Center
    ) {


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BotonDeWifi()
            BotonDeDatos( )
        }

    }
}

@Composable
fun BotonDeWifi() {
    //Tenemos que hacer el trackeo en caso de que no este
    var context = LocalContext.current
    val networkWifiWatches = Networkwatches()
    var enableWifiWatcher by rememberSaveable {
        mutableStateOf(networkWifiWatches.redesEstanDisponibles(context = context))
    }

    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth(),
        enabled = enableWifiWatcher
    ) {
        Text(text = "Informacion Por Medio del Bluetooh", modifier = Modifier.padding(end = 12.dp))

        Icon(imageVector = Icons.Filled.Info, contentDescription = "wifiLogo")

    }
}

@Composable
fun BotonDeDatos() {
    Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(10.dp)) {
        Text(
            text = "Informacion Por Medio de Datos Moviles",
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp)
        )
        Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "redLogo")
    }
}
