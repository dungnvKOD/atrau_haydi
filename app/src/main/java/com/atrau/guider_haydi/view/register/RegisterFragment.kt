package com.atrau.guider_haydi.view.register


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atrau.guider_haydi.view.MainActivity
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(), View.OnClickListener, RegisterViewFListener {

    private var country_code: String? = null
//    private lateinit var onRegisterListenner: OnRegisterListenner

    private val registerFPresenter = RegisterFPresenter(this)

    companion object {
        val TAG = "RegisterFragment"

        @SuppressLint("StaticFieldLeak")
        val newFragment = RegisterFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {

        txt_code_phone.text = "+${(activity as MainActivity).area}"
        registerFPresenter.countries()
        btn_back_login.setOnClickListener(this)
        btn_register.setOnClickListener(this)
    }


    override fun onClick(v: View?) {

        when (v!!.id) {

            R.id.btn_back_login -> {
                (activity as MainActivity).toast("btn_back_login...")
                (activity as MainActivity).popbacktask()
            }

            R.id.btn_register -> {

                val name: String = edt_name_register.text.toString().trim()

//                val phone: String = (activity as MainActivity).region + edt_phone_register.text.toString().trim()
                val phone: String = edt_phone_register.text.toString().trim()

                val password: String = edt_password_register.text.toString().trim()
                val email: String = edt_email_register.text.toString().trim()
                val address: String = edt_address.text.toString().trim()
                val lat: String = App.getMyInsatnce().lat.toString()
                val lon: String = App.getMyInsatnce().lon.toString()
                if (check_box_login.isChecked) {
                    //TODO...
                    registerFPresenter.register(name, phone, password, email, address, country_code!!, lat, lon)
                } else {
                    (activity as MainActivity).toast("Bạn đã đồng ý với chính sách của HAYDI")
                }
            }
        }
    }


    private fun spinner(array: MutableList<MutableList<String>>) {
        val arr: MutableList<String> = mutableListOf()
        for (i in 0 until array.size) {
            arr.add(i, array[i][1])
            Log.d(TAG, "   ${arr[i]}")

        }
        country_code = array[0][3]

        if (arr != null) {
            spinner.setItems(arr)
            spinner.selectedIndex = 0
            spinner.setOnItemSelectedListener { view, position, id, item ->

                country_code = array[position][3]
                Snackbar.make(
                        view,
                        " $item  $country_code",
                        Snackbar.LENGTH_LONG
                ).show()
            }
        }


    }


    override fun getCountriesSuccess(array: MutableList<MutableList<String>>) {
        spinner(array)
        for (i in 0 until array.size) {
            if ((activity as MainActivity).code != null && array[i][3] == (activity as MainActivity).code) {
                txt_code_phone.text = array[i][4]
                break
            }
        }

    }


    override fun getCountriesFaile() {

    }


    override fun nameIsEmpty() {
        edt_name_register.error = "Hãy nhập tên"

    }

    override fun phoneIsEmpty() {
        edt_phone_register.error = "Hãy nhập số điện thoại"

    }

    override fun passwordIsEmpty() {
        edt_password_register.error = "Hãy nhập mật khẩu"

    }

    override fun emailIsEmpty() {
        edt_email_register.error = "Hãy nhập email"
    }

    override fun emailInvalid() {
        edt_email_register.error = "Email không đúng"

    }

    override fun phoneInvalid() {
        edt_phone_register.error = "Số điện thoại không đúng"

    }

    override fun passwordShort() {
        edt_password_register.error = "Mật khẩu ngắn"
    }

    override fun nameInvalid() {
        edt_name_register.error = "Tên không hợp lệ"
    }


    override fun registerSuccess(id: String, name: String, phone: String, token: String) {
        (activity as MainActivity).toast("dang ky thanhf cong")
//        onRegisterListenner.onFinlFromLogin(phone)

        edt_name_register.setText("")
        edt_phone_register.setText("")
        edt_password_register.setText("")
        edt_email_register.setText("")
        edt_address.setText("")

    }

    override fun registerFale(message: String) {
        (activity as MainActivity).toast(message)

    }


}
