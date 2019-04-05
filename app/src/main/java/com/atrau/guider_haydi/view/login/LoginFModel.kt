package com.atrau.guider_haydi.view.login


import android.util.Log
import com.atrau.guider_haydi.webservice.Client
import com.atrau.guider_haydi.App
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFModel(val loginFListener: LoginFListener) {

    companion object {
        val TAG = "LoginFModel"
    }

    fun login(phone: String, password: String) {

        Log.d(TAG, "lol   ")
        val call: Call<ResponseBody> = Client.getService()!!.postLogin(phone, password)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loginFListener.loginFale("loi...")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.code() == 200) { //thanh cong
//                    Log.d(TAG, "dung true...${response.body()!!.string()}")
                    val jsonOBJECT = JSONObject(response.body()!!.string())
                    val jsonObj = jsonOBJECT.getJSONObject("data")
                    val name = jsonObj.getString("name")
                    val phone = jsonObj.getString("phone")
                    val token = jsonObj.getString("token")
                    App.getMyInsatnce().token = token

                    Log.d(TAG, "dung...$token")
                    loginFListener.loginSuccess()

                } else { //that bai
                    val jsonObject = JSONObject(response.errorBody()!!.string())
                    val message = jsonObject.getString("message")
                    loginFListener.loginFale(message)
                    Log.d(TAG, "dung false...${response.errorBody()!!.string()}")
                }
            }
        })
    }

    fun getGeo() {
        val call: Call<ResponseBody> = Client.getService()!!.getGeo()

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loginFListener.getGeoFaile()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                Log.d(TAG, "dung...." + response.body()!!.string())
                if (response.code() == 200) { //thanh cong

                    val jsonOBJECT = JSONObject(response.body()!!.string())
                    val jsonObject = jsonOBJECT.getJSONObject("data")
                    val country = jsonObject.getString("country")
                    val region = jsonObject.getString("region")
                    val eu = jsonObject.getString("eu")
                    val timezone = jsonObject.getString("timezone")
                    val city = jsonObject.getString("city")
                    var lat: Double = 0.0
                    var lon: Double = 0.0
                    val ll: JSONArray = jsonObject.getJSONArray("ll")
                    for (i in 0 until ll.length()) {
                        if (i == 0) {
                            lat = ll[0] as Double
                        } else {
                            lon = ll[1] as Double
                        }
                    }

                    val metro = jsonObject.getString("metro")
                    val area = jsonObject.getString("area")
                    loginFListener.getGeoSuccess(
                        country,
                        region,
                        eu,
                        timezone,
                        city,
                        lat,
                        lon,
                        metro,
                        area
                    )

                    Log.d(TAG, "dung ok...$country \n $region \n $eu \n $timezone \n $city \n $lat")

                } else { //that bai
                    Log.d(TAG, "dung false...${response.errorBody()!!.string()}")
                    //TODO...
                }
            }
        })
    }

    fun getImage() {
        val call: Call<ResponseBody> = Client.getService()!!.getImage()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loginFListener.loginFale("loi...")

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.code() == 200) { //thanh cong
//                    Log.d(TAG, "IMAGE  ...${response.body()!!.string()}")
                    val obj = JSONObject(response.body()!!.string())
                    val data = obj.getJSONObject("data")
                    val banner = data.getString("banner")
                    loginFListener.getImageSuccess(banner)
                } else { //that bai

                }
            }
        })
    }
}