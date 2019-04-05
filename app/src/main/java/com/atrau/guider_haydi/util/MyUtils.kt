package com.atrau.guider_haydi.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import com.atrau.guider_haydi.R
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
//            if (datestr != null || datestr != "null" || datestr != "") {
//                return 0
//            }
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



    var firebaseDBDate = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    var firebaseDBDay = "yyyy-MM-dd"
    val NOW_TIME_RANGE = DateUtils.MINUTE_IN_MILLIS * 5 // 5 minutes

    var dateTime = "yyyy-MM-dd HH:mm:ss"

    fun getFirebaseDateFormat(): SimpleDateFormat {
        val cbDateFormat = SimpleDateFormat(firebaseDBDate)
        cbDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return cbDateFormat
    }

    @SuppressLint("SimpleDateFormat")
    fun formatFirebaseDay(date: Date): String {
        val cbDateFormat = SimpleDateFormat(firebaseDBDay)
        cbDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return cbDateFormat.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateTime(date: Date): String {
        val cbDateFormat = SimpleDateFormat(dateTime)
        cbDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return cbDateFormat.format(date)
    }

    fun getRelativeTimeSpanString(context: Context, time: Long): CharSequence {
        val now = System.currentTimeMillis()
        val range = Math.abs(now - time)

        return if (range < NOW_TIME_RANGE) {
            context.getString(R.string.now_time_range)
        } else DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)

    }

    fun getRelativeTimeSpanStringShort(context: Context, time: Long): CharSequence {
        val now = System.currentTimeMillis()
        val range = Math.abs(now - time)
        return formatDuration(context, range, time)
    }

    private fun formatDuration(context: Context, range: Long, time: Long): CharSequence {
        val res = context.resources
        if (range >= DateUtils.WEEK_IN_MILLIS + DateUtils.DAY_IN_MILLIS) {
            return shortFormatEventDay(context, time)
        } else if (range >= DateUtils.WEEK_IN_MILLIS) {
            val days = ((range + DateUtils.WEEK_IN_MILLIS / 2) / DateUtils.WEEK_IN_MILLIS).toInt()
            return String.format(res.getString(R.string.duration_week_shortest), days)
        } else if (range >= DateUtils.DAY_IN_MILLIS) {
            val days = ((range + DateUtils.DAY_IN_MILLIS / 2) / DateUtils.DAY_IN_MILLIS).toInt()
            return String.format(res.getString(R.string.duration_days_shortest), days)
        } else if (range >= DateUtils.HOUR_IN_MILLIS) {
            val hours = ((range + DateUtils.HOUR_IN_MILLIS / 2) / DateUtils.HOUR_IN_MILLIS).toInt()
            return String.format(res.getString(R.string.duration_hours_shortest), hours)
        } else if (range >= NOW_TIME_RANGE) {
            val minutes = ((range + DateUtils.MINUTE_IN_MILLIS / 2) / DateUtils.MINUTE_IN_MILLIS).toInt()
            return String.format(res.getString(R.string.duration_minutes_shortest), minutes)
        } else {
            return res.getString(R.string.now_time_range)
        }
    }

    private fun shortFormatEventDay(context: Context, time: Long): String {
        val flags = DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_ABBREV_MONTH
        val f = Formatter(StringBuilder(50), Locale.getDefault())
        return DateUtils.formatDateRange(context, f, time, time, flags).toString()
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

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }



}