package com.example.comunicaciones

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.GMobiledata
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentManager.BackStackEntry
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.ComunicacionesTheme
import com.example.comunicaciones.Auxiliares.Routes
import com.example.comunicaciones.DatosRed.DatosRedMenuInfo
import com.example.comunicaciones.DatosRed.DatosRedViewModel
import com.example.comunicaciones.Map.LocationsManager
import com.example.comunicaciones.Map.MapViewModel
import com.example.comunicaciones.Wifi.WifiInfoViewModel

import com.example.comunicaciones.Wifi.menuDeInfoWifi
import com.example.comunicaciones.data.IdManager
import com.google.firebase.FirebaseApp


class MainActivity : ComponentActivity() {


    private val viewModel: MapViewModel by viewModels()

    private val uuid:String = IdManager.getGeneratedId()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){
            permisos ->

        when{
            permisos.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION,false) ->{

                LocationsManager.Builder
                    .create(this@MainActivity)
                    .request(onUpdateLocation = {latitude: Double, longitude: Double ->
                        viewModel.latitude.value = latitude
                        viewModel.longitude.value = longitude
                    })
            }


        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            ComunicacionesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    locationPermissionRequest.launch(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ))
                    val navigationController = rememberNavController()
                    //No te olvides de hacer una pantalla de carga para poder agarrarlo
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.HomeLogin.route
                    ) {
                        composable(Routes.HomeLogin.route) { HomeScreen(navigationController) }
                        composable(Routes.WifiInfoScreen.route) {
                            menuDeInfoWifi(
                                context = applicationContext,
                                navigationController = navigationController,
                                wifiInfoViewModel = WifiInfoViewModel(),
                                latitud = viewModel.latitude.value,
                                Longitud = viewModel.longitude.value,
                                id = uuid
                            )
                        }
                        composable(Routes.RedInfoScreen.route){
                            DatosRedMenuInfo(
                                context = applicationContext,
                                datosRedViewModel = DatosRedViewModel(),
                                navigationController = navigationController,
                                latitud = viewModel.latitude.value,
                                Longitud = viewModel.longitude.value,
                                id = uuid
                            )
                        }

                    }

                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigationController: NavHostController) {


    Scaffold(
        topBar = { Topbar() }, modifier = Modifier
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            ElevatedButton(
                onClick = {

                    navigationController.navigate(Routes.WifiInfoScreen.route)
                }, shape = RoundedCornerShape(10.dp), modifier = Modifier
                    .height(50.dp)
                    .width(250.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFffdad3), // Puedes cambiar este color según tus necesidades
                    contentColor = Color.Black
                )

            ) {
                Icon(imageVector = Icons.Filled.Wifi, contentDescription = "Icono del Wifi")
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Obtener datos del WIFI")
            }
            Spacer(modifier = Modifier.height(40.dp))
            ElevatedButton(
                onClick = { navigationController.navigate(Routes.RedInfoScreen.route) }, shape = RoundedCornerShape(10.dp), modifier = Modifier
                    .height(50.dp)
                    .width(250.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFffdad3), // Puedes cambiar este color según tus necesidades
                    contentColor = Color.Black
                )

            ) {
                Icon(
                    imageVector = Icons.Filled.GMobiledata,
                    contentDescription = "Icono de los datos"
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Obtener datos Moviles", style = TextStyle(
                    )
                )

            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topbar() {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "BIENVENIDO",
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

        )

}
