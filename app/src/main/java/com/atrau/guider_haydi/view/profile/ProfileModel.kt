package com.atrau.guider_haydi.view.profile

import android.util.Log
import com.atrau.guider_haydi.dto.GuideDto
import com.atrau.guider_haydi.conmon.Constant
import com.atrau.guider_haydi.dto.JobDto
import com.atrau.guider_haydi.dto.Skill
import com.atrau.guider_haydi.webservice.Client
import com.atrau.guider_haydi.App
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileModel(val onProfileListenner: OnProfileListenner) {

    companion object {
        val TAG = "ProfileModel"
    }

    fun getProfile(token: String, check: String) {
        val call: Call<ResponseBody> = Client.getService()!!.getProfile(App.getMyInsatnce().token)
//        Log.d(TAG, App.getMyInsatnce().token)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                Log.d(TAG, response.body()!!.string())
                if (response.code() == 200) { //thanh cong
                    val jsonObject = JSONObject(response.body()!!.string())
                    val jsonObj = jsonObject.getJSONObject("data")

                    val id = jsonObj.getString("id")
                    val name = jsonObj.getString("name")
                    val phone = jsonObj.getString("phone")
                    val facebookId = jsonObj.getString("facebook_id")
                    val password = jsonObj.getString("password")
                    val source = jsonObj.getString("source")
                    val email = jsonObj.getString("email")
                    val subPhone = jsonObj.getString("sub_phone")
                    val address = jsonObj.getString("address")
                    val countryCode = jsonObj.getString("country_code")
                    val level = jsonObj.getString("level")
                    val status = jsonObj.getString("status")
                    val type = jsonObj.getString("type")
                    val rate = jsonObj.getString("rate")
                    val voteTotal = jsonObj.getString("vote_total")
                    val orderTotal = jsonObj.getString("order_total")
                    val languages = jsonObj.getString("languages")
                    val quote = jsonObj.getString("quote")
                    val myValue = jsonObj.getString("my_value")
                    val description = jsonObj.getString("description")
                    var price = jsonObj.getString("price")
                    val priceUnit = jsonObj.getString("unit")
                    val lat = jsonObj.getString("lat")
                    val lon = jsonObj.getString("lon")
                    val cover = jsonObj.getString("cover")
                    val avatar = jsonObj.getString("avatar")
                    val createdAt = jsonObj.getString("created_at")
                    val updatedAt = jsonObj.getString("updated_at")
                    val jobs = jsonObj.getString("jobs")
                    if (price == "null" || price == "" || price == null) {
                        price = "0"
                    }
                    val guideDto = GuideDto(
                        id,
                        name,
                        phone,
                        facebookId,
                        password,
                        source,
                        email,
                        subPhone,
                        address,
                        countryCode,
                        level,
                        status.toInt(),
                        type,
                        rate,
                        voteTotal.toInt(),
                        orderTotal.toInt(),
                        languages,
                        quote,
                        myValue,
                        description,
                        price.toInt(),
                        priceUnit,
                        lat,
                        lon,
                        cover,
                        avatar,
                        createdAt,
                        updatedAt, jobs
                    )
                    if (check == Constant.ALL) {
                        onProfileListenner.onGetProfileSuccess(guideDto)
                        Log.d(TAG, "ALL")
                    } else if (check == Constant.AVATAR) {
                        onProfileListenner.getAvatar(guideDto)

                    } else if (check == Constant.PRICE) {
                        onProfileListenner.getPrice(guideDto)

                    } else if (check == Constant.PHONE) {
                        onProfileListenner.getPhone(guideDto)

                    } else if (check == Constant.EMAIL) {
                        onProfileListenner.getEmail(guideDto)

                    } else if (check == Constant.MY_VALUE) {
                        Log.d(TAG, "MY_VALUE")
                        onProfileListenner.getMyValue(guideDto)

                    } else if (check == Constant.DESCRRIPTION) {
                        onProfileListenner.getDescription(guideDto)

                    } else if (check == Constant.LANGUAGES) {
                        onProfileListenner.getLangguages(guideDto)

                    } else if (check == Constant.COVER) {
                        onProfileListenner.getCover(guideDto)
                    } else if (check == Constant.JOB) {
                        onProfileListenner.getMyJob(guideDto)

                    }

                } else { //that bai
                    val jsonObject = JSONObject(response.errorBody()!!.string())
                    val jsonObj = jsonObject.getJSONObject("data")
                    val message = jsonObj.getString("message")
                    onProfileListenner.onGetProfileFaile()
                }
            }
        })
    }

    fun putProfile(guideDto: GuideDto, type: String) {

        Log.d(TAG, "dung..." + guideDto.languages)
        //TODO
        val call: Call<ResponseBody> =
            Client.getService()!!.putProfile(App.getMyInsatnce().token, guideDto)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                Log.d(TAG, "putProfile... Success")
                if (response.code() == 200) {
                    //thành cong
                    Log.d(TAG, "putProfile... Success")
                    getProfile(App.getMyInsatnce().token, type)
                } else {
                    val jsonObject = JSONObject(response.errorBody()!!.string())
//                    val message = jsonObject.getString("message")
                    Log.d(TAG, "dung123 true" + response.errorBody()!!.string())
                    Log.d(TAG, "putProfile... Failure")
                }
            }
        })
    }


    fun getSkill(token: String) {

        val call: Call<ResponseBody> = Client.getService()!!.getSkill(token)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.code() == 200) { //thanh cong
//                    Log.d(TAG, "${response.body()!!.string()}...")    D/ProfileModel: {"error":false,"status":200,"data":[{"id":1,"name":"Lái xe","level":2},{"id":3,"name":"Đặt vé tham quan","level":4},{"id":4,"name":"Đặt vé tàu","level":1}]}...
                    val jsonObject = JSONObject(response.body()!!.string())
                    val jsonArr = jsonObject.getJSONArray("data")
                    val skills: ArrayList<Skill> = ArrayList()

                    for (i in 0 until jsonArr.length()) {
                        val skillJson: JSONObject? = jsonArr.getJSONObject(i)
                        val id: Int = skillJson!!.getInt("id")
                        val name: String = skillJson.getString("name")
                        val level: Double = skillJson.getDouble("level")
                        val icon = skillJson.getString("icon")
                        val desc = skillJson.getString("desc")
                        val skill: Skill = Skill(id, name, level, icon, desc)
//                        val skill: Skill = Skill(id, name, level)
                        skills.add(skill)

                    }
//                    Log.d(TAG, "onResponse ...${skills.size}")
//                    Log.d(TAG, "onResponse ...${response.body()!!.string()}")
                    onProfileListenner.getSkillSuccess(skills)
                } else { //that bai


                }
            }
        })
    }


    fun getJob() {
        val call: Call<ResponseBody> = Client.getService()!!.getJob()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {

                    val jsonOBJ = JSONObject(response.body()!!.string())
                    val jsonArr = jsonOBJ.getJSONArray("data")
                    val jobs: ArrayList<JobDto> = ArrayList()
                    for (i in 0 until jsonArr.length()) {
                        val jsonObj = jsonArr.getJSONObject(i)
                        val id = jsonObj.getInt("id")
                        val name = jsonObj.getString("name")
                        val nameEn = jsonObj.getString("name_en")
                        val icon = jsonObj.getString("icon")

                        val jobDto = JobDto(id, name, nameEn, icon, false)
                        jobs.add(jobDto)
                    }

                    onProfileListenner.getJob(jobs)
                }
            }
        })
    }



}