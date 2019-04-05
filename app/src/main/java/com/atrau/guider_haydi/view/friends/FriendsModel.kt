package com.atrau.guider_haydi.view.friends

import android.util.Log
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.dto.Friends
import com.atrau.guider_haydi.webservice.Client
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendsModel(var onFriendsLisstener: OnFriendsLisstener) {
    companion object {
        val TAG = "FriendsModel"
    }

    fun getFriends(limit: Int, offset: Int) {

        val call: Call<ResponseBody> =
            Client.getService()!!.getFriends(
                App.getMyInsatnce().token
                , limit, offset, "created_at:desc"

            )


        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
//                    Log.d(TAG, response.body()!!.string() + "....xxx.")
                    val jsonObject = JSONObject(response.body()!!.string())
                    val jsonArr = jsonObject.getJSONArray("data")


                    val arrFriends: ArrayList<Friends> = ArrayList()
                    for (i in 0 until jsonArr.length()) {
                        val job = jsonArr.getJSONObject(i)
                        val name = job.getString("name")
                        val id = job.getInt("id")
                        val avatat = job.getString("avatar")
                        val nickname = job.getString("nickname")
                        val type = job.getString("type")
//                        val createdAt = job.getString("created_at")
                        val friends = Friends(name, id, avatat, nickname, type, "")

                        arrFriends.add(friends)
                    }

                    onFriendsLisstener.getFriends(arrFriends)

                } else {

                }
            }
        })
    }
}