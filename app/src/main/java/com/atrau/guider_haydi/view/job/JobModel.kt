package com.atrau.guider_haydi.view.job

import android.util.Log
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.dto.JobDto
import com.atrau.guider_haydi.view.profile.ProfileModel
import com.atrau.guider_haydi.webservice.Client
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobModel(val onJobModel: OnJobModel) {


    fun putJob(hashMap: HashMap<String, ArrayList<Int>>, jobType: String) {
        Log.d(ProfileModel.TAG, "$hashMap  ...dung")


        val call: Call<ResponseBody> = Client.getService()!!.putJob(App.getMyInsatnce().token, hashMap)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    //Goi  update job
                    onJobModel.putJob()
                    Log.d(ProfileModel.TAG, "ok....${response.body()!!.string()}")
                }
            }
        })
    }

}