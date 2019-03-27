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
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.dto.Campaign
import com.atrau.guider_haydi.view.message_group.MessageGroupFragment

import com.google.firebase.iid.FirebaseInstanceId
//import com.google.firebase.FirebaseApp
//import com.google.firebase.iid.FirebaseInstanceId

import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
//    var arrSkill: ArrayList<Skill> = ArrayList()

    companion object {
        val TAG = "HomeActivity"
        var isAppRunning: Boolean = false
    }

    lateinit var campaign: Campaign

    private lateinit var onImageListener: OnImageListener
    var arrSkill: ArrayList<Skill>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()
    }

//    override fun onResume() {
//        super.onResume()
//        if (!isAppRunning){
//            popbacktask()
//            addOrShowFragment(NewTripFragment.newFragment, R.id.frame_home, false, false)
//        }
//
//    }

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


        addOrShowFragment(NewTripFragment.newFragment, R.id.frame_home, false, false)


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

    /**
     *  fragment.....
     *
     */

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
