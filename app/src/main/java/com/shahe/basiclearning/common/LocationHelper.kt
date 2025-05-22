package com.shahe.basiclearning.common

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import android.location.Geocoder
import java.io.IOException
import java.util.Locale


object LocationHelper {

    @SuppressLint("MissingPermission")
    fun getCityNameFromLocation(context: Context, onResult: (String?) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(context, Locale.getDefault())
                try {
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    val cityName = addresses?.firstOrNull()?.locality
                    onResult(cityName)
                } catch (e: IOException) {
                    e.printStackTrace()
                    onResult(null)
                }
            } else {
                onResult(null)
            }
        }.addOnFailureListener {
            it.printStackTrace()
            onResult(null)
        }
    }
}