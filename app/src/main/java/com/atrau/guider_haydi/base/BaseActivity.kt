package com.atrau.guider_haydi.base

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.atrau.guider_haydi.R


open class BaseActivity :AppCompatActivity() {

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

    fun addOrShowFragment(f: androidx.fragment.app.Fragment, rootId: Int, b: Boolean) {
        val tag = f.javaClass.name
        val fm = supportFragmentManager
        var fragment: androidx.fragment.app.Fragment? = fm.findFragmentByTag(tag)

        if (fragment != null) {
            val frms: ArrayList<androidx.fragment.app.Fragment> =
                    fm.fragments as ArrayList<androidx.fragment.app.Fragment>

            for (frm: androidx.fragment.app.Fragment in frms) {

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
    }

    fun popbacktask() {
        supportFragmentManager.popBackStack()
    }

}