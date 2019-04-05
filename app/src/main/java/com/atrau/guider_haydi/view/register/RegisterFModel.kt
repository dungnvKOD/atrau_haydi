package com.atrau.guider_haydi.view.register


import android.util.Log
import com.atrau.guider_haydi.webservice.Client
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFModel(val registerFListener: RegisterFListener) {
    companion object {
        val TAG = "RegisterFModel"
    }

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
        //TODO

        val call: Call<ResponseBody> =
            Client.getService()!!.postRegister(email, phone, password, name, address, country_code, lat, lon, unit)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                registerFListener.registerFale(" loi roi")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    //thành cong

                    val jsonObject: JSONObject = JSONObject(response.body()!!.string())
                    val jsonObj = jsonObject.getJSONObject("data")
                    val id = jsonObj.getString("id")
                    val name = jsonObj.getString("name")
                    val phone = jsonObj.getString("phone")
                    val token = jsonObj.getString("token")

//                    Log.d(TAG, "$id \n $name \n $phone \n $token")
                    registerFListener.registerSuccess(id, name, phone, token)

                } else {

                    val jsonObject = JSONObject(response.errorBody()!!.string())
                    val message = jsonObject.getString("message")

                    Log.d(TAG, response.errorBody()!!.string())
                    registerFListener.registerFale(message)
                }
            }
        })
    }

    fun countries() {

        val call: Call<ResponseBody> = Client.getService()!!.getCountries()

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                registerFListener.getCountriesFaile()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                var array: MutableList<MutableList<String>> = mutableListOf()
                // ý tưởng là cho tạo thành 1 ma trận n chiều nhưng bị nỗi :(( hơi đen

                if (response.code() == 200) { //thanh cong

                    val jsonObject = JSONObject(response.body()!!.string())
                    val jsonArray = jsonObject.getJSONArray("data")
                    Log.d(TAG, "dung ${jsonArray.length()}")
                    for (i in 0 until jsonArray.length()) {

                        val jsonObj = jsonArray.getJSONObject(i)
                        val id = jsonObj.getInt("id")
                        val name = jsonObj.getString("name")
                        val image = jsonObj.getString("image")
                        val code = jsonObj.getString("code")
                        val phoneCode = jsonObj.getString("phone_code")

                        val arr: MutableList<String> = mutableListOf<String>()
                        arr.add(0, id.toString())
                        arr.add(1, name)
                        arr.add(2, image)
                        arr.add(3, code)
                        arr.add(4, phoneCode)

                        array.add(arr)
                        Log.d(TAG, "dung true...$id \n $name \n $image \n $code \n $phoneCode")

                    }

                    registerFListener.getCountriesSuccess(array)
                } else { //that bai

                    Log.d(TAG, "dung false...${response.errorBody()!!.string()}")


                }
            }
        })
    }

}