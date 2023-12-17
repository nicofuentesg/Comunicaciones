package com.example.comunicaciones.DatosRed

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint.Style

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ReadMore
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.comunicaciones.Auxiliares.Routes
import com.example.comunicaciones.LoadingAnimation
import com.example.comunicaciones.Wifi.DatosCelular
import com.example.comunicaciones.Wifi.UbicacionCelular
import com.example.comunicaciones.Wifi.WifiInfoViewModel

import com.example.comunicaciones.data.UsuarioDataClass
import com.example.comunicaciones.data.UsuarioDataViewModel
import com.example.comunicaciones.firabase.SaveDataViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatosRedMenuInfo(
    context: Context, datosRedViewModel: DatosRedViewModel, navigationController: NavHostController,
    latitud: Double,
    Longitud: Double,
    id: String
) {


    val loading: Boolean by datosRedViewModel.loading.observeAsState(initial = true)

    if (loading) {
        LoadingButtonRed(context = context, datosRedViewModel = datosRedViewModel)

    } else {
        //Aqui agregamos cuando ya se cargo todo

        //Declraamos el viewmodelDe datos
        val usuarioDataViewModel = remember {
            UsuarioDataViewModel()
        }
        usuarioDataViewModel.id = id
        usuarioDataViewModel.lat = latitud
        usuarioDataViewModel.long = Longitud
        Scaffold(topBar = {
            topBarRedInfo(
                navigationController,
                datosRedViewModel,
                context,
                usuarioDataViewModel
            )
        }
        ) {

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
                verticalArrangement = Arrangement.Center,
                content = {
                    item {
                        Spacer(modifier = Modifier.height(56.dp)) // Espacio en blanco para separar la TopAppBar y el contenido
                    }
                    item {
                        DatosCelular(usuarioDataViewModel)
                        RedInfoSpeed(
                            datosRedViewModel = datosRedViewModel,
                            context = context,
                            usuarioDataViewModel = usuarioDataViewModel
                        )
                        RedInfoProvideer(
                            datosRedViewModel = datosRedViewModel,
                            context = context,
                            usuarioDataViewModel = usuarioDataViewModel
                        )
                        UbicacionCelular(latitud, Longitud, usuarioDataViewModel)
                    }
                })

        }


    }


}


@Composable
fun LoadingButtonRed(context: Context, datosRedViewModel: DatosRedViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadingAnimation()
        //Aqui llamariamos para inicializar la carga
        datosRedViewModel.obtenerProovedorDatosMoviles(context)

    }
}


@Composable
fun RedInfoSpeed(
    datosRedViewModel: DatosRedViewModel,
    context: Context,
    usuarioDataViewModel: UsuarioDataViewModel
) {

    val redSpeed: Int by datosRedViewModel.velocidadRedMovil.observeAsState(initial = 0)
    datosRedViewModel.obtenerVelocidadRedMovil(context)
    usuarioDataViewModel.velocidadInternet = redSpeed

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFffdad3),
        modifier = Modifier
            .height(210.dp)
            .padding(10.dp),
        shadowElevation = 10.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.wrapContentSize(),
                    color = Color(0xFFD1D5E1)
                ) {
                    Text(
                        text = "Velocidad de Internet",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$redSpeed",
                    fontSize = 24.sp,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "La velocidad esta dada en Mb/s", style = TextStyle(
                        color = Color.Black,
                    )
                )

                Spacer(modifier = Modifier.height(2.dp))

            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(width = 100.dp, height = 140.dp),
                color = Color(0xFFeebdb7)
            ) {
                Icon(
                    imageVector = Icons.Filled.Speed,
                    contentDescription = "Velocidad de internet",
                    tint = Color.White
                )                //Aqui va la imagen
            }
        }
    }


}

@Composable
fun RedInfoProvideer(
    datosRedViewModel: DatosRedViewModel,
    context: Context,
    usuarioDataViewModel: UsuarioDataViewModel
) {

    val redProvider: String by datosRedViewModel.nombreDeProovedro.observeAsState(initial = "")

    datosRedViewModel.obtenerProovedorDatosMoviles(context)

    usuarioDataViewModel.proveedorInternet = redProvider

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFffdad3),
        modifier = Modifier
            .height(210.dp)
            .padding(10.dp),
        shadowElevation = 10.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.wrapContentSize(),
                    color = Color(0xFFD1D5E1)
                ) {
                    Text(
                        text = "Proovedor de Internet",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$redProvider",
                    fontSize = 24.sp,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Es nuestro proovedor de internet", style = TextStyle(
                        color = Color.Black,
                    )
                )

                Spacer(modifier = Modifier.height(2.dp))


            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(width = 100.dp, height = 140.dp),
                color = Color(0xFFeebdb7)
            ) {
                Icon(
                    imageVector = Icons.Filled.Speed,
                    contentDescription = "Velocidad de internet",
                    tint = Color.White
                )
            }
        }
    }


}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarRedInfo(
    navigationController: NavHostController,
    redViewModel: DatosRedViewModel,
    context: Context,
    usuarioDataViewModel: UsuarioDataViewModel
) {

    var guardadoButon by rememberSaveable {
        mutableStateOf(true)
    }

    var mostrarMenu by rememberSaveable {
        mutableStateOf(false)
    }
    val usuario = UsuarioDataClass(
        id = usuarioDataViewModel.id,
        dispositivoMarca = usuarioDataViewModel.dispositivoMarca,
        dispositivoModelo = usuarioDataViewModel.dispositivoModelo,
        lat = usuarioDataViewModel.lat,
        long = usuarioDataViewModel.long,
        velocidadInternet = usuarioDataViewModel.velocidadInternet,
        proveedorInternet = usuarioDataViewModel.proveedorInternet,
        distanciaAlaRed = usuarioDataViewModel.distanciaAlaRed
    )

    val firebaseViewModel = SaveDataViewModel()


    val contexto = LocalContext.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "DATOS MOVILES",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
            navigationIconContentColor = Color.Black,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black
        ),
        navigationIcon = {
            IconButton(onClick = {
                navigationController.navigate(Routes.HomeLogin.route)
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
            }
        },
        actions = {
            IconButton(onClick = {
                mostrarMenu = !mostrarMenu
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu Opciones")

            }
            Box(Modifier.padding(top = 18.dp).background(Color.White)) {
                DropdownMenu(expanded = mostrarMenu, onDismissRequest = { mostrarMenu = false }) {
                    DropdownMenuItem(modifier = Modifier.background(Color.White),
                        text = {
                            Row {
                                Text(
                                    text = "Actualizar", style = TextStyle(color = Color.Black)
                                )
                                Spacer(modifier = Modifier.padding(8.dp))
                                Icon(
                                    imageVector = Icons.Filled.Update,
                                    contentDescription = "Actualizar",
                                    tint = Color.Black
                                )
                            }
                        },
                        onClick = {
                            navigationController.navigate(Routes.RedInfoScreen.route)
                            firebaseViewModel.UpdateDataRed(usuario)
                        }
                    )
                    DropdownMenuItem(modifier = Modifier.background(Color.White),
                        text = {
                            Row {
                                Text(
                                    text = "Guardando", style = TextStyle(color = Color.Black)
                                )
                                Spacer(modifier = Modifier.padding(8.dp))
                                Icon(
                                    imageVector = Icons.Filled.Save,
                                    contentDescription = "Actualizar",
                                    tint = Color.Black
                                )
                            }
                        },
                        onClick = {
                            firebaseViewModel.saveDataRed(usuario)
                            Toast.makeText(contexto, "Guardado", Toast.LENGTH_SHORT).show()
                            guardadoButon = false
                        },
                        enabled = guardadoButon )

                }

            }
        }
    )


}