package com.atrau.guider_haydi.view.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.atrau.guider_haydi.conmon.Constant

class LoginFPresenter(context: Context, val loginViewFListener: LoginViewFListener) :
    LoginFListener {


    private val loginFModel = LoginFModel(this)
    val pref: SharedPreferences = context.getSharedPreferences(Constant.KEY_PHONE, Context.MODE_PRIVATE)

    fun login(phone: String, password: String) {

        if (TextUtils.isEmpty(phone)) {
            loginViewFListener.phoneIsEmpty()
            return
        }
        if (phone.length == 10) {
            if (phone.substring(0, 1) == "0") {
                //TODO Số điện thoại hợp lệ
                if (TextUtils.isEmpty(password)) {
                    loginViewFListener.passwordIsEmpty()
                    return
                } else {
                    loginFModel.login(phone, password)
                    return
                }
            } else {
                //TODO số điện thoại không hợp lệ!
                loginViewFListener.phoneInvalid()
                return
            }
        } else {
            //TODO Độ dài chuỗi không hợp lệ!
            loginViewFListener.phoneInvalid()
            return
        }
    }


    fun getGeo() {
        loginFModel.getGeo()
    }

    fun getImage() {
        loginFModel.getImage()
    }

    @SuppressLint("CommitPrefEdits")
    fun loginLogout(isLogin: Boolean) {
        val edit: SharedPreferences.Editor = pref.edit()
        edit.putBoolean(Constant.KEY_LOGIN, isLogin)
        edit.apply()
    }

    fun getLoginLogout(): Boolean? {
        val isLogin = pref.getBoolean(Constant.KEY_LOGIN, false)
        return isLogin
    }
    fun rememberLoginLogout(phone: String, password: String) {

        val edit: SharedPreferences.Editor = pref.edit()


            edit.putString(Constant.KEY_PHONE_NUMBER_LOGIN, phone)
            edit.putString(Constant.KEY_PASSWORD_LOGIN, password)
        edit.apply()
    }

    fun getPasswordLoginLogout(): String? {
        val password = pref.getString(Constant.KEY_PASSWORD_LOGIN, "")
        return password
    }
    fun getPhoneLoginLogout(): String? {
        val phone = pref.getString(Constant.KEY_PHONE_NUMBER_LOGIN, "")
        return phone
    }


    fun rememberUser(phone: String, password: String, status: Boolean) {

        val edit: SharedPreferences.Editor = pref.edit()

        if (!status) {
            //xoa luu tru truoc do
            edit.clear()
        } else {
            edit.putString(Constant.KEY_PHONE_NUMBER, phone)
            edit.putString(Constant.KEY_PASSWORD, password)
            edit.putBoolean(Constant.KEY_REMEMBER, status)
        }
        edit.apply()
    }

    fun getPhone(): String? {
        val phone = pref.getString(Constant.KEY_PHONE_NUMBER, "")
        return phone
    }

    fun getPassword(): String? {
        val password = pref.getString(Constant.KEY_PASSWORD, "")
        return password
    }

    fun getStatus(): Boolean {
        return pref.getBoolean(Constant.KEY_REMEMBER, false)

    }

    override fun loginSuccess() {
        loginViewFListener.loginSuccess()

    }

    override fun loginFale(message: String) {
        loginViewFListener.loginFale(message)

    }

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
        loginViewFListener.getGeoSuccess(country, region, eu, timezone, city, lat, lon, metro, area)
    }

    override fun getGeoFaile() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun getImageSuccess(banner: String) {
        loginViewFListener.getImageSuccess(banner)
    }
}