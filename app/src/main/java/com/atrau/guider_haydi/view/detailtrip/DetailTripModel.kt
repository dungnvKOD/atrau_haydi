package com.atrau.guider_haydi.view.detailtrip

import android.util.Log
import com.atrau.guider_haydi.dto.DetailTrip
import com.atrau.guider_haydi.webservice.Client
import com.atrau.guider_haydi.App
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTripModel(val detailTripListener: DetailTripListener) {

    companion object {
        val TAG = "DetailTripModel"
//        val token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MywibmFtZSI6ImJhZGEiLCJwaG9uZSI6IjA5ODM3MzI4MDYiLCJpYXQiOjE1NTExMDUyODUsImV4cCI6MTU1MzY5NzI4NX0.aFZ8EZDzbsd_X8NhoYdGDGLBC5fLfSwTAJe8yiZYcxw"
    }

    fun getDetailTrip(id: String) {

        Log.d(TAG, id)
        val call: Call<ResponseBody> =
            Client.getService()!!.getDetailTrip(
                App.getMyInsatnce().token
                ,
                id.toString()
            )


        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
//                    val detailTrip: ResponseBody = response.body()!!
//                    Log.d(TAG, response.body()!!.string())
                    val jsonObject = JSONObject(response.body()!!.string())
                    val jsonArr = jsonObject.getJSONArray("data")


                    //                    val detailTrip:DetailTrip= DetailTrip(data.getInt("id"),data.getInt("user_id"))
                    for (i in 0 until jsonArr.length()) {

                        val jsonObj=jsonArr.getJSONObject(i)
                        val id = jsonObj.getInt("id")
                        val userId = jsonObj.getInt("user_id")
                        val tripId = jsonObj.getInt("trip_id")
                        val guiderId = jsonObj.getInt("guider_id")
                        val startDay = jsonObj.getString("start_day")
                        val endDay = jsonObj.getString("end_day")
                        val hours = jsonObj.getInt("hours")
                        val timeMeet = jsonObj.getString("time_meet")
                        val addressMeet = jsonObj.getString("address_meet")
                        val lat = jsonObj.getString("lat")
                        val lon = jsonObj.getString("lon")
                        val note = jsonObj.getString("note")
                        val money = jsonObj.getInt("money")
                        val totalMoney = jsonObj.getString("total_money")
                        val paymentStatus = jsonObj.getString("payment_status")
                        val paymentType = jsonObj.getString("payment_type")
//                        val online = jsonObj.getString("online")
                        val status = jsonObj.getString("status")
                        val cancelId = jsonObj.getString("cancel_id")
                        val createdAt = jsonObj.getString("created_at")
                        val updatedAt = jsonObj.getString("updated_at")
                        val name = jsonObj.getString("name")
                        Log.d(TAG, name)
                        val avatar = jsonObj.getString("avatar")
//                        val rate = jsonObj.getString("rate")
//                        val voteTotal = jsonObj.getInt("vote_total")
                        val address = jsonObj.getString("address")
                        val phone = jsonObj.getString("phone")
                        val email = jsonObj.getString("email")
                        val tripAddress = jsonObj.getString("trip_address")
                        val tripStartDay = jsonObj.getString("trip_start_day")
                        val tripEndDay = jsonObj.getString("trip_end_day")
                        val peoples = jsonObj.getInt("peoples")

                        val detailTrip = DetailTrip(
                            id,
                            userId,
                            tripId,
                            guiderId,
                            startDay,
                            endDay,
                            hours,
                            timeMeet,
                            addressMeet,
                            lat,
                            lon,
                            note,
                            money,
                            totalMoney,
                            paymentStatus,
                            paymentType,
                            "",
                            status,
                            cancelId,
                            createdAt,
                            updatedAt,
                            name,
                            avatar,
                            "",
                            0,
                            phone,
                            email,
                            tripAddress,
                            tripStartDay,
                            tripEndDay,
                            peoples
                        )

                        detailTripListener.getDetailTripSuccess(detailTrip)
                        Log.d(TAG, detailTrip.startDay.toString() + "dung......")
                        break
                    }


                } else {
                    detailTripListener.getDetailTripFailed()
                    Log.d(TAG, "false...")
                }
            }
        })
    }


    fun putDetailTrip(status: HashMap<String, String>) {


        Log.d(TAG, "put...$status")
        val call: Call<ResponseBody> =
            Client.getService()!!.putDetailTrip(
                App.getMyInsatnce().token,
                App.getMyInsatnce().idTrip.toString(),
                status
            )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    Log.d(TAG, response.body()!!.string() + "dung...")

                } else {
                    detailTripListener.getDetailTripFailed()
                    Log.d(TAG, "false...")
                }
            }
        })
    }
}