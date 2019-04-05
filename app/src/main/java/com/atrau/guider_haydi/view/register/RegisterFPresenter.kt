package com.atrau.guider_haydi.view.register


import android.text.TextUtils
import com.atrau.guider_haydi.conmon.Constant

class RegisterFPresenter(private val onRegisterViewFListener: RegisterViewFListener) :
    RegisterFListener {


    private val registerFModel = RegisterFModel(this)

    fun register(
        name: String,
        phone: String,
        password: String,
        email: String,
        address: String,
        country_code: String,
        lat: String,
        lon: String,
        unit: String
    ) {

        val emailType = email.matches(Constant.EMAIL_TYPE.toRegex())
        val nameType = name.matches(Constant.NAME_TYPE.toRegex())

        if (TextUtils.isEmpty(name)) {
            //kiem tra ten co rong hay khong
            onRegisterViewFListener.nameIsEmpty()
            return
        }
        if (!nameType) {
            //kiem tra ten co rong hay khong
            onRegisterViewFListener.nameInvalid()
            return
        }

        if (TextUtils.isEmpty(email)) {
            onRegisterViewFListener.emailIsEmpty()
            return
        }

        if (!emailType) {//kiem tra email hop le
            onRegisterViewFListener.emailInvalid()
            return
        }
        if (TextUtils.isEmpty(password)) {
            onRegisterViewFListener.passwordIsEmpty()
            return
        }
        if (password.length < 6) {
            onRegisterViewFListener.passwordShort()
            return
        }

        if (TextUtils.isEmpty(phone)) {
            //kiem tra ten co rong hay khong
            onRegisterViewFListener.phoneIsEmpty()
            return
        }


        if (phone.length in 10..12) {
            //TODO Số điện thoại hợp lệ
            registerFModel.register(name, phone, password, email, address, country_code, lat, lon, unit)
            return

        } else {
            //TODO Độ dài chuỗi không hợp lệ!
            onRegisterViewFListener.phoneInvalid()
            return
        }
    }

    fun countries() {
        registerFModel.countries()
    }

    override fun registerSuccess(id: String, name: String, phone: String, token: String) {
        onRegisterViewFListener.registerSuccess(id, name, phone, token)
    }

    override fun registerFale(message: String) {
        onRegisterViewFListener.registerFale(message)
    }


    override fun getCountriesSuccess(array: MutableList<MutableList<String>>) {
        onRegisterViewFListener.getCountriesSuccess(array)

    }

    override fun getCountriesFaile() {
        onRegisterViewFListener.getCountriesFaile()
    }
}