package com.atrau.guider_haydi.view.login


import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.atrau.guider_haydi.view.HomeActivity
import com.atrau.guider_haydi.view.MainActivity
import com.atrau.guider_haydi.view.register.RegisterFragment
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.base.BaseActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_login.*
import java.io.IOException
import java.util.*

class LoginFragment : Fragment(), View.OnClickListener,
    LoginViewFListener, MainActivity.OnListenner {

    private lateinit var loginFPresenter: LoginFPresenter
    private lateinit var rootView: View

    companion object {
        @SuppressLint("StaticFieldLeak")
        val newFragment = LoginFragment()
        val TAG = "LoginFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginFPresenter =
            LoginFPresenter((activity as BaseActivity), this)
        init()
    }

    private fun init() {
        (activity as MainActivity).setOnListenner(this)
        btn_forgot_password.setOnClickListener(this)
        btn_register_login.setOnClickListener(this)
        btn_login.setOnClickListener(this)

        Log.d(TAG,loginFPresenter.getLoginLogout()!!.toString())
        if (loginFPresenter.getLoginLogout()!!) {
            loginFPresenter.login(loginFPresenter.getPhoneLoginLogout()!!, loginFPresenter.getPasswordLoginLogout()!!)
            return
        }




        loginFPresenter.getImage()
        val phone: String? = loginFPresenter.getPhone()
        val password: String? = loginFPresenter.getPassword()
        if (loginFPresenter.getStatus()) {
            loginFPresenter.rememberUser(
                edt_phone_login.text.toString().trim(),
                edt_password_login.text.toString().trim(),
                true
            )
            check_box_login.isChecked = true

        } else {
            check_box_login.isChecked = false

        }

        if (password != null && phone != null) {
            edt_phone_login.setText(phone)
            edt_password_login.setText(password)
        }


        // đăng ký interface
//        RegisterFragment().setOnFinlFromLogin(this) //dang ky set phone tu register sang login

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_login -> {
                val phone: String = edt_phone_login.text.toString().trim()
                val password: String = edt_password_login.text.toString().trim()
                loginFPresenter.login(phone, password)
                loginFPresenter. loginLogout(true)
                loginFPresenter.rememberLoginLogout(phone, password)

                if (check_box_login.isChecked) {
                    loginFPresenter.rememberUser(phone, password, true)
                } else {
                    loginFPresenter.rememberUser(phone, password, false)
                }
            }

            R.id.btn_register_login -> {
                (activity as MainActivity).addOrShowFragment(RegisterFragment.newFragment, R.id.main_layout_, true)
            }
            // quen mat khau
            R.id.btn_forgot_password -> {
                //TODO
//                (activity as MainActivity).toast("btn_forgot_password")
            }
        }
    }


    override fun onGetMyLocationSuccess(lat: Double, lon: Double) {
        //TODO... se kiem tra la nuoc nao
        App.getMyInsatnce().lat = lat
        App.getMyInsatnce().lon = lon

        getAddress(lat, lon)
    }

    override fun onGetMyLocationFaile() {
        //bat dau goi api de lay location
        loginFPresenter.getGeo()
    }


    override fun phoneIsEmpty() {
        edt_phone_login.error = "Hãy nhập số điện thoại"
    }

    override fun passwordIsEmpty() {
        edt_password_login.error = "Hãy nhập mật khẩu"
    }

    override fun phoneInvalid() {
        edt_phone_login.error = "Số điện thoại không đúng"

    }

    override fun loginSuccess() {
        //TODO...
        loginFPresenter.loginLogout(true)
        val intent: Intent = Intent((activity), HomeActivity::class.java)
        startActivity(intent)




        (activity as MainActivity).finish()
    }

    override fun loginFale(message: String) {
        (activity as MainActivity).toast("$message")

    }

    fun getAddress(lat: Double, lng: Double) {
        val geocoder = Geocoder((activity as MainActivity), Locale.getDefault())
        try {

            val addresses = geocoder.getFromLocation(lat, lng, 1)
            val obj = addresses[0]
            var add = obj.getAddressLine(0)

            add = add + "\n" + obj.countryName
            add = add + "\n" + obj.countryCode
            add = add + "\n" + obj.adminArea
            add = add + "\n" + obj.postalCode
            add = add + "\n" + obj.subAdminArea
            add = add + "\n" + obj.locality
            add = add + "\n" + obj.subThoroughfare
            Log.v("IGA", "Address$add")

            (activity as MainActivity).code = obj.countryCode
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }
    //lay vi tri

    override fun getGeoSuccess(
        country: String,
        region: String,
        eu: String,
        timezone: String,
        city: String,
        lat: Double,
        lon: Double,
        metro: String,
        area: String
    ) {
        //TODO cho nay phai add vao vieuregister
        //

        (activity as MainActivity).area = area
        App.getMyInsatnce().lat = lat
        App.getMyInsatnce().lon = lon


    }

    override fun getGeoFaile() {


    }

    override fun getImageSuccess(banner: String) {
        Glide.with(activity!!)
            .load(banner)
            .into(img_title)
        (activity as MainActivity).linkSetting = banner

    }


}
