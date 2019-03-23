package com.atrau.guider_haydi.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest


class MyLocation(private val context: Context, val onGetLocation: OnGetLocation) {


    private var locationRequest: LocationRequest = LocationRequest.create()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

//    private lateinit var myLocation: Location
//
//    private var locationCallback: LocationCallback? = null


    companion object {
        val TAG = "MyLocation"
    }

    init {
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 1000
        setUpLocation()
    }

    private fun setUpLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        getLocationDevice()

    }

    fun getLocationDevice() {

        if (MyUtils.isGpsOpen(context)) {

            getMyLocation()
            Toast.makeText(context, "da bat", Toast.LENGTH_LONG).show()

        } else {

            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)


            val client = LocationServices.getSettingsClient(context)
            val task = client.checkLocationSettings(builder.build())

            task.addOnFailureListener { exception ->

                if (exception is ResolvableApiException) {

                    Toast.makeText(context, "chua bat", Toast.LENGTH_LONG).show()

//                    (context as MainActivity).openGPS(exception)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")

    fun getMyLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            Log.d(TAG, "đã vào lấy dc location")
            if (location != null) {
                Log.d(TAG, "${location.latitude}")
                onGetLocation.getLocation(location.latitude, location.longitude)
            } else {
                //đang test ở đây vi may ảo đang k lấy dc location
                onGetLocation.getLocation(21.0, 105.0)
            }
        }
    }


    interface OnGetLocation {
        fun getLocation(lat: Double, lon: Double)
    }
}

