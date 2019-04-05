package com.atrau.guider_haydi.view.job

import com.atrau.guider_haydi.dto.JobDto
import com.atrau.guider_haydi.webservice.Client
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobModel(val onJobModel: OnJobModel) {


    fun getJob() {
        val call: Call<ResponseBody> = Client.getService()!!.getJob()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {


            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {

//                    Log.d(TAG, response.body()!!.string())

                    val jsonOBJ = JSONObject(response.body()!!.string())
                    val jsonArr = jsonOBJ.getJSONArray("data")
                    val jobs: ArrayList<JobDto> = ArrayList()
                    for (i in 0 until jsonArr.length()) {
                        val jsonObj = jsonArr.getJSONObject(i)
                        val id = jsonObj.getInt("id")
                        val name = jsonObj.getString("name")
                        val nameEn = jsonObj.getString("name_en")
                        val icon = jsonObj.getString("icon")

                        val jobDto = JobDto(id, name, nameEn, icon)
                        jobs.add(jobDto)
                    }
                    onJobModel.getJob(jobs)
                }


            }
        })
    }
}