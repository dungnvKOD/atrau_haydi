package com.atrau.guider_haydi.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat

import java.util.*


class MyUtils {

    companion object {
        //        const val TYPE_DATE_D_M_Y = "d-M-Y"
        const val TYPE_DATE_D_M_YYYY = "d-M-yyyy"
        const val TYPE_DATE_HH_MM_a = "hh:mm a"
        const val TYPE_DATE_HH_MM = "hh:mm"
        const val TYPE_DATE_FULL = "E, d/M/yyy hh:mm:ss a"

        @SuppressLint("SimpleDateFormat")
        fun convertTime(time: Long, type: String): String {
            val calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat(type)
            val date = Date(time)
            sdf.timeZone = TimeZone.getDefault()
            return sdf.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        @RequiresApi(Build.VERSION_CODES.O)
        fun dateToLong(datestr: String): Long {
            if (datestr != null || datestr != "null" || datestr != "") {
                return 0
            }
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = SimpleDateFormat("dd-MM-yyyy")
            val date = inputFormat.parse(datestr)
            val formattedDate = outputFormat.format(date)
            Log.d("1", "..." + formattedDate.toString())
            val sdf = SimpleDateFormat(TYPE_DATE_D_M_YYYY)
            val dateOut = sdf.parse(formattedDate)
            val millis = dateOut.time
            return millis
        }


        fun isGpsOpen(context: Context): Boolean {//new bta roi thi tim vi tri con chua bat thif bat len
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)//neu khac null va dc dc kich hoat
        }

    }


    /**
     *  lay thoi gian hien tai
     */
    fun timeHere(): Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis
    }

    /**
     *  lay dia danh ...
     */

//    fun hereLocation(lat: Double, lon: Double, context: Context): String {
//        var city = ""
//        val geocoder = Geocoder(context, Locale.getDefault())
//        val addresses: List<Address>
//        try {
//
//            addresses = geocoder.getFromLocation(lat, lon, 15)
//            if (addresses.isNotEmpty()) {
//                for (adr: Address in addresses) {
//                    if (adr.locality != null) {
//                        city = adr.locality
//                        return city
//                    }
//                }
//            }
//        } catch (e: Exception) {
//        }
//        return city
//    }


}