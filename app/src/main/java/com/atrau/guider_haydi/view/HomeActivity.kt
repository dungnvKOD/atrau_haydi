package com.atrau.guider_haydi.view

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.atrau.guider_haydi.dto.Skill
import com.atrau.guider_haydi.view.campaign.CampaignFragment
import com.atrau.guider_haydi.view.lincome.LincomeFragment
import com.atrau.guider_haydi.view.newtrip.NewTripFragment
import com.atrau.guider_haydi.view.profile.ProfileFragment
import com.google.android.gms.tasks.OnCompleteListener
import android.app.NotificationManager
import android.app.NotificationChannel
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.conmon.Constant
import com.atrau.guider_haydi.dto.Campaign
import com.atrau.guider_haydi.dto.GuideDto
import com.atrau.guider_haydi.view.message_group.MessageGroupFragment
import com.atrau.guider_haydi.view.profile.ProfileModel
import com.atrau.guider_haydi.webservice.Client

import com.google.firebase.iid.FirebaseInstanceId
//import com.google.firebase.FirebaseApp
//import com.google.firebase.iid.FirebaseInstanceId

import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
//    var arrSkill: ArrayList<Skill> = ArrayList()

    companion object {
        val TAG = "HomeActivity"
        var isAppRunning: Boolean = false
    }

    lateinit var campaign: Campaign
    private lateinit var newTripFragment: NewTripFragment


    private lateinit var onImageListener: OnImageListener
    var arrSkill: ArrayList<Skill>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        newTripFragment = NewTripFragment.newFragment
        addOrShowFragment(newTripFragment, R.id.frame_home, false, false)
        init()
        Log.d(TAG, "onCreate...")

    }

    private fun init() {


        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = token
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "1"
        val channel2 = "2"

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                "Channel 1", NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.description = "This is BNT"
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationManager.createNotificationChannel(notificationChannel)

            val notificationChannel2 = NotificationChannel(
                channel2,
                "Channel 2", NotificationManager.IMPORTANCE_MIN
            )

            notificationChannel.description = "This is bTV"
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationManager.createNotificationChannel(notificationChannel2)

            Log.d(TAG, "dung1222121")

        }


        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_trip -> {
                    addOrShowFragment(NewTripFragment.newFragment, R.id.frame_home, false, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_campaign -> {
                    addOrShowFragment(CampaignFragment.newFragment, R.id.frame_home, false, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_income -> {
                    addOrShowFragment(LincomeFragment.newFrgament, R.id.frame_home, false, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_message -> {
                    addOrShowFragment(MessageGroupFragment.newFragment, R.id.frame_home, false, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_profile -> {
                    addOrShowFragment(ProfileFragment.newFragment, R.id.frame_home, false, false)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

//        App.getMyInsatnce().idUser = getProfile()
        getProfile()
    }


    /**
     *  request permisstion .....
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     *  thong bao ...
     *
     */

    fun toast(message: String) {
        return Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart...")
    }

    override fun onPause() {
        super.onPause()

//        finish()
        Log.d(TAG, "onPause...")
    }


    override fun onDestroy() {
        super.onDestroy()
        newTripFragment.popBackTask()
        supportFragmentManager.beginTransaction().remove(newTripFragment).commit()
        Log.d(TAG, "onDestroy...")
    }

    fun getProfile() {
        val call: Call<ResponseBody> = Client.getService()!!.getProfile(App.getMyInsatnce().token)
        Log.d(ProfileModel.TAG, App.getMyInsatnce().token)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.code() == 200) { //thanh cong
                    val jsonObject = JSONObject(response.body()!!.string())
                    val jsonObj = jsonObject.getJSONObject("data")

                    val id = jsonObj.getString("id")
                    val name = jsonObj.getString("name")
                    val phone = jsonObj.getString("phone")
                    val facebookId = jsonObj.getString("facebook_id")
                    val password = jsonObj.getString("password")
                    val source = jsonObj.getString("source")
                    val email = jsonObj.getString("email")
                    val subPhone = jsonObj.getString("sub_phone")
                    val address = jsonObj.getString("address")
                    val countryCode = jsonObj.getString("country_code")
                    val level = jsonObj.getString("level")
                    val status = jsonObj.getString("status")
                    val type = jsonObj.getString("type")
                    val rate = jsonObj.getString("rate")
                    val voteTotal = jsonObj.getString("vote_total")
                    val orderTotal = jsonObj.getString("order_total")
                    val languages = jsonObj.getString("languages")
                    val quote = jsonObj.getString("quote")
                    val myValue = jsonObj.getString("my_value")
                    val description = jsonObj.getString("description")
                    var price = jsonObj.getString("price")
                    val priceUnit = jsonObj.getString("unit")
                    val lat = jsonObj.getString("lat")
                    val lon = jsonObj.getString("lon")
                    val cover = jsonObj.getString("cover")
                    val avatar = jsonObj.getString("avatar")
                    val createdAt = jsonObj.getString("created_at")
                    val updatedAt = jsonObj.getString("updated_at")
                    val jobs = jsonObj.getString("jobs")
                    if (price == "null" || price == "" || price == null) {
                        price = "0"
                    }
                    val guideDto = GuideDto(
                        id,
                        name,
                        phone,
                        facebookId,
                        password,
                        source,
                        email,
                        subPhone,
                        address,
                        countryCode,
                        level,
                        status.toInt(),
                        type,
                        rate,
                        voteTotal.toInt(),
                        orderTotal.toInt(),
                        languages,
                        quote,
                        myValue,
                        description,
                        price.toInt(),
                        priceUnit,
                        lat,
                        lon,
                        cover,
                        avatar,
                        createdAt,
                        updatedAt, jobs
                    )

                    App.getMyInsatnce().guideDto = guideDto


                } else { //that bai
                    val jsonObject = JSONObject(response.errorBody()!!.string())
                    val jsonObj = jsonObject.getJSONObject("data")
                    val message = jsonObj.getString("message")
                }
            }
        })
    }


    fun addOrShowFragment(f: Fragment, rootId: Int, b: Boolean, anim: Boolean) {
        val tag = f.javaClass.name
        val fm = supportFragmentManager
        val fragment: Fragment? = fm.findFragmentByTag(tag)
        if (anim) {
            if (fragment != null) {
                val frms: ArrayList<Fragment> =
                    fm.fragments as ArrayList<Fragment>

                for (frm: Fragment in frms) {

                    fm.beginTransaction()
                        .setCustomAnimations(
                            R.anim.left_enter,
                            R.anim.left_exit,
                            R.anim.right_enter,
                            R.anim.right_exit
                        )
                        .hide(frm)
                        .commit()
                }
                fm.beginTransaction()
                    .show(f)
                    .commit()

            } else {
                if (b) {

                    fm.beginTransaction()
                        .setCustomAnimations(
                            R.anim.left_enter,
                            R.anim.left_exit,
                            R.anim.right_enter,
                            R.anim.right_exit
                        )
                        .add(rootId, f, tag)
                        .addToBackStack(null)
                        .commit()
                } else {
                    fm.beginTransaction()
                        .setCustomAnimations(
                            R.anim.left_enter,
                            R.anim.left_exit,
                            R.anim.right_enter,
                            R.anim.right_exit
                        )

                        .add(rootId, f, tag)
                        .commit()
                }
            }
        } else {
            if (fragment != null) {
                val frms: ArrayList<Fragment> =
                    fm.fragments as ArrayList<Fragment>

                for (frm: Fragment in frms) {

                    fm.beginTransaction()
//                    .setCustomAnimations(
//                        R.anim.left_enter,
//                        R.anim.left_exit,
//                        R.anim.right_enter,
//                        R.anim.right_exit
//                    )
                        .hide(frm)
                        .commit()
                }
                fm.beginTransaction()
                    .show(f)
                    .commit()

            } else {
                if (b) {

                    fm.beginTransaction()
//                    .setCustomAnimations(
//                        R.anim.left_enter,
//                        R.anim.left_exit,
//                        R.anim.right_enter,
//                        R.anim.right_exit
//                    )
                        .add(rootId, f, tag)
                        .addToBackStack(null)
                        .commit()
                } else {
                    fm.beginTransaction()
//                    .setCustomAnimations(
//                        R.anim.left_enter,
//                        R.anim.left_exit,
//                        R.anim.right_enter,
//                        R.anim.right_exit
//                    )

                        .add(rootId, f, tag)
                        .commit()
                }
            }

        }

    }

    fun popbacktask() {
        supportFragmentManager.popBackStack()
    }

    fun setOnImageListener(onImageListener: OnImageListener) {

        this.onImageListener = onImageListener
    }

    interface OnImageListener {
        fun setImageListener(imageBitmap: Bitmap)


    }

}
