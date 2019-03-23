package com.atrau.guider_haydi.view.login


import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginFPresenter =
            LoginFPresenter((activity as BaseActivity), this)
        init()
    }

    private fun init() {

        loginFPresenter.getImage()
        val phone: String? = loginFPresenter.getPhone()
        val password: String? = loginFPresenter.getPassword()

        if (loginFPresenter.getStatus() == true) {
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
        (activity as MainActivity).setOnListenner(this)
        btn_forgot_password.setOnClickListener(this)
        btn_register_login.setOnClickListener(this)
        btn_login.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_login -> {

                val phone: String = edt_phone_login.text.toString().trim()
                val password: String = edt_password_login.text.toString().trim()
                loginFPresenter.login(phone, password)

                if (check_box_login.isChecked) {
                    loginFPresenter.rememberUser(phone, password, true)
                } else {
                    loginFPresenter.rememberUser(phone, password, false)
                }
                //TODO đang test...
//                val intent: Intent = Intent((activity), HomeActivity::class.java)
//                startActivity(intent)
//                (activity as MainActivity).finish()

            }

            R.id.btn_register_login -> {
                (activity as MainActivity).addOrShowFragment(RegisterFragment.newFragment, R.id.main_layout_, true)
            }
            // quen mat khau
            R.id.btn_forgot_password -> {
                //TODO
                (activity as MainActivity).toast("btn_forgot_password")
            }
        }
    }


    override fun onGetMyLocationSuccess(lat: Double, lon: Double) {
        //TODO... se kiem tra la nuoc nao
        App.getMyInsatnce().lat = lat
        App.getMyInsatnce().lon = lon

        getAddress(lat, lon)

        Toast.makeText(activity, "dung : $lat $lon", Toast.LENGTH_LONG).show()
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
        val intent: Intent = Intent((activity), HomeActivity::class.java)
        startActivity(intent)
        (activity as MainActivity).finish()
    }

    override fun loginFale(message: String) {
        //TODO...
//        (activity as MainActivity).toast("api$message")

    }

    fun getAddress(lat: Double, lng: Double) {
        val geocoder = Geocoder((activity as MainActivity), Locale.getDefault())
        try {

            val addresses = geocoder.getFromLocation(lat, lng, 1)
            val obj = addresses[0]
            var add = obj.getAddressLine(0)
            Log.v("IGA", "Address$obj")


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

        Log.d(TAG, "country $country region $region area $area")
        (activity as MainActivity).area = area
        App.getMyInsatnce().lat = lat
        App.getMyInsatnce().lon = lon
        Toast.makeText(activity, " lay vi tri tren api", Toast.LENGTH_LONG).show()


    }

    override fun getGeoFaile() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun getImageSuccess(banner: String) {
        Glide.with(activity!!)
                .load(banner)
                .into(img_title)

        Log.d(TAG,banner+" dung")
    }


}
