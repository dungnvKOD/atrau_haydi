package com.atrau.guider_haydi.view.newtrip


import android.util.Log
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.webservice.Client
import com.atrau.guider_haydi.view.skill.SkillModel
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewTripModel(val onNewTripListener: NewTripListener) {

    companion object {
        val TAG = "NewTripModel"

        val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MywibmFtZSI6ImJhZGEiLCJwaG9uZSI6IjA5ODM3MzI4MDYiLCJpYXQiOjE1NTExMDUyODUsImV4cCI6MTU1MzY5NzI4NX0.aFZ8EZDzbsd_X8NhoYdGDGLBC5fLfSwTAJe8yiZYcxw"
    }

    fun getMerchant(status: String) {
        val call: Call<ResponseBody> =
                Client.getService()!!.getMerchant(
                    App.getMyInsatnce().token,
                        status
                )

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {


                val listMerchant: ArrayList<Array<Any>> = ArrayList()

                if (response.code() == 200) {

//                    Log.d(TAG,response.body()!!.string()) //dang 1 item ma len view 2 item
                    //thành cong
                    val jsonObject = JSONObject(response.body()!!.string())
                    val jsonObj = jsonObject.getJSONObject("data")
                    val total = jsonObj.getInt("total")
                    val list = jsonObj.getJSONArray("list")
                    for (i in 0 until list.length()) {
                        val obj = list.getJSONObject(i)
                        val id = obj.getInt("id")
                        val startDay: String = obj.getString("start_day")
                        val endDay: String = obj.getString("end_day")
                        val hours: Int = obj.getInt("hours")
                        val customerName: String = obj.getString("customer_name")
                        val avatar: String = obj.getString("avatar")
                        val status: String = obj.getString("status")
                        val paymentStatus: String = obj.getString("payment_status")
                        Log.d(TAG, "dung all... : $status  $paymentStatus")
                        val objMerchant: Array<Any> =
                                arrayOf(startDay, endDay, hours, customerName, avatar, status, paymentStatus, id)
                        listMerchant.add(objMerchant)
                    }

                    onNewTripListener.getMerchantSuccess(total, listMerchant)


//                    Log.d(TAG, status + "  dung...")

                } else {
                    onNewTripListener.getAllMerchantFailed()
                    Log.d(SkillModel.TAG, "false...")
                }
            }
        })

    }

    fun getAllMerchant(limit: Int, offset: Int) {
        val call: Call<ResponseBody> =
                Client.getService()!!.getAllMerchant(App.getMyInsatnce().token, limit, offset)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                val listMerchant: ArrayList<Array<Any>> = ArrayList()

                if (response.code() == 200) {
                    //thành cong
                    val jsonObject = JSONObject(response.body()!!.string())
                    val jsonObj = jsonObject.getJSONObject("data")
                    val total = jsonObj.getInt("total")
                    val list = jsonObj.getJSONArray("list")
                    for (i in 0 until list.length()) {
                        val obj = list.getJSONObject(i)
                        val id = obj.getInt("id")
                        val startDay: String = obj.getString("start_day")
                        val endDay: String = obj.getString("end_day")
                        val hours: Int = obj.getInt("hours")
                        val customerName: String = obj.getString("customer_name")
                        val avatar: String = obj.getString("avatar")
                        val status: String = obj.getString("status")
                        val paymentStatus: String = obj.getString("payment_status")

                        val objMerchant: Array<Any> =
                                arrayOf(startDay, endDay, hours, customerName, avatar, status, paymentStatus, id)
                        listMerchant.add(objMerchant)
                    }

                    onNewTripListener.getAllMerchantSuccess(total, listMerchant)
                    Log.d(TAG, "dung all... : " + response.body()!!.string())

                } else {
                    onNewTripListener.getAllMerchantFailed()
                    Log.d(SkillModel.TAG, "false...")
                }
            }
        })

    }
}