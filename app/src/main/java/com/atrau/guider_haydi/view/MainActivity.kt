package com.atrau.guider_haydi.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.app.ActivityCompat
import com.atrau.guider_haydi.view.login.LoginFragment
import com.atrau.guider_haydi.base.BaseActivity
import com.atrau.guider_haydi.conmon.Constant
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.util.MyLocation
import com.google.android.gms.common.api.ResolvableApiException

class MainActivity : BaseActivity(), MyLocation.OnGetLocation {
    var code: String? = null


    companion object {
        val TAG = "MainActivity"
    }

    private lateinit var onListenner: OnListenner
    private lateinit var location: MyLocation
    var area: String? = null
    var linkSetting: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.main_layout_, LoginFragment.newFragment)
            .commit()
        init()
    }

    private fun init() {
        location = MyLocation(this@MainActivity, this)
        checkLocationPermistion()
        val pInfo = this.packageManager.getPackageInfo(packageName, 0)
    }

    //check location
    private fun checkLocationPermistion() {
        if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            //TODO gọi fun lấy vị trí
            location.getMyLocation()

        } else {
            toast("chua dc cap ")
            requestPermissionsSafely(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constant.LOCATION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //đồng ý cấp quyền
            //TODO gọi fun lấy vị trí
            location.getMyLocation()

//            onListenner.onGetMyLocationSuccess()
        } else {
            //không dc cấp quyền thì sẽ lấy từ api
            onListenner.onGetMyLocationFaile()

        }
    }

    fun openGPS(e: ResolvableApiException) {
        e.startResolutionForResult(this@MainActivity, Constant.REQUEST_GPS)
    }

    //TODO đoanj này chưa test dc ... chưa có máy thật
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constant.REQUEST_GPS -> {
                if (resultCode == Activity.RESULT_OK) {
                    Handler().postDelayed({
                        location.getMyLocation()

                    }, 3000)
                }
            }
        }
    }

    override fun getLocation(lat: Double, lon: Double) {
        //da lay dc vi tri
        onListenner.onGetMyLocationSuccess(lat, lon)
    }

    fun setOnListenner(onListenner: OnListenner) {
        this.onListenner = onListenner
    }

    //lang nghe viec laasy vij tri
    interface OnListenner {
        fun onGetMyLocationSuccess(lat: Double, lon: Double)
        fun onGetMyLocationFaile()

    }
}
