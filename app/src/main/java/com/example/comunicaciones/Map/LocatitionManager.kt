package com.example.comunicaciones.Map

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Looper
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.lang.ref.WeakReference

object LocationsManager {


    private lateinit var activity: WeakReference<ComponentActivity>
    private lateinit var locationRequest: com.google.android.gms.location.LocationRequest

    private var onUpdateLocation: WeakReference<(latitude: Double, longitude: Double) -> Unit>? =
        null

    private val locationCallBack = object : LocationCallback() {
        override fun onLocationAvailability(locationAvailability: LocationAvailability) {
            super.onLocationAvailability(locationAvailability)

            activity.get()?.let {
                if (!locationAvailability.isLocationAvailable) {
                    //vamos a settings screen
                    goSettingsScreen(it)
                    removeCallBack(it)

                }

            }

        }

        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            onUpdateLocation?.get()?.invoke(
                locationResult.lastLocation?.latitude ?: 0.0,
                locationResult.lastLocation?.longitude ?: 0.0
            )
        }
    }


    object Builder {
        fun build(): Builder {
            return this
        }

        fun create(activity: ComponentActivity): LocationsManager {
            LocationsManager.activity = WeakReference(activity)
            locationRequest = com.google.android.gms.location.LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000).apply {
                setMinUpdateIntervalMillis(1000)
                setMaxUpdateAgeMillis(0)
                setMinUpdateDistanceMeters(1.0f)
                setGranularity(Granularity.GRANULARITY_FINE)
            }.build()
            return LocationsManager

        }

    }

    private fun goSettingsScreen(activity: Activity){
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

    fun request(onUpdateLocation: ((latitude: Double, longitude: Double) -> Unit)? = null){
        LocationsManager.onUpdateLocation = WeakReference(onUpdateLocation)
        activity.get()?.let {
            if (ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                //vamos a settings
                goSettingsScreen(it)
                return
            }
            LocationServices.getFusedLocationProviderClient(it).requestLocationUpdates(
                locationRequest, locationCallBack, Looper.getMainLooper()
            )
        }
    }


    private fun removeCallBack(activity: Activity) {
        LocationServices.getFusedLocationProviderClient(activity).flushLocations()
        LocationServices.getFusedLocationProviderClient(activity).removeLocationUpdates(
            locationCallBack
        )

    }
}