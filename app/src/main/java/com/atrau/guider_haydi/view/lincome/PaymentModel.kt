package com.atrau.guider_haydi.view.lincome

import android.util.Log
import com.atrau.guider_haydi.dto.Payment
import com.atrau.guider_haydi.webservice.Client
import com.atrau.guider_haydi.App
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentModel(val onPamentListener: OnPaymentListener) {

    companion object {
        val TAG = "PaymentModel"
    }

    fun postPayment(body: HashMap<String, Any>) {

        val call: Call<ResponseBody> =
            Client.getService()!!.postPayment(
                App.getMyInsatnce().token,
                body
            )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, "failsed..")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.code() == 200) {
                    Log.d(TAG, "ok......")
                    getPaymentHistory(10, 0)
                } else {

                    val jsonObj = JSONObject(response.errorBody()!!.string())
                    val sum = jsonObj.getString("message")
                    onPamentListener.postPaymentFailed(sum)
                }
            }
        })
    }

    fun getPaymentHistory(limit: Int, offset: Int) {

        val call: Call<ResponseBody> =
            Client.getService()!!.getPaymentHistory(App.getMyInsatnce().token, limit, offset)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    val jsonOBJ = JSONObject(response.body()!!.string())
                    val data = jsonOBJ.getJSONObject("data")
                    val total = data.getInt("total")
                    val jsonArr = data.getJSONArray("list")

                    val payments: ArrayList<Payment> = ArrayList()

                    for (i in 0 until jsonArr.length()) {
                        val jsonObj = jsonArr.getJSONObject(i)
                        val status = jsonObj.getInt("status")
                        var price = jsonObj.getString("total_money")
                        val priceUnit = jsonObj.getString("unit")
                        val day = jsonObj.getString("created_at")

                        if (price == null || price == "null" || price == "") {
                            price = "0"
                        }
                        val payment = Payment(price.toInt(), priceUnit, status, day)
                        payments.add(payment)
                    }
                    onPamentListener.getHistoryPayment(payments, total)
                } else {

                }
            }
        })
    }

    fun getSumPayment() {

        val call: Call<ResponseBody> =
            Client.getService()!!.getSumPayment(App.getMyInsatnce().token)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

//                Log.d(TAG,response.body()!!.string())
                if (response.code() == 200) {
                    val jsonObj = JSONObject(response.body()!!.string())
                    Log.d(TAG,jsonObj.getString("data"))
                    if (jsonObj.getString("data") !!.toString() != "null") {
                        val data: JSONObject? = jsonObj.getJSONObject("data")
                        val sum = data!!.getString("total")
                        onPamentListener.getSumPayment(sum)
                    } else {
                        val sum = "0"
                        onPamentListener.getSumPayment(sum)
                    }
                } else {


                }
            }
        })
    }

}