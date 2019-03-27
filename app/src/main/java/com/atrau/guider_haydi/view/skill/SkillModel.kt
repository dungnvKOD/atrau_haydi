package com.atrau.guider_haydi.view.skill

import android.util.Log
import com.atrau.guider_haydi.dto.Skill
import com.atrau.guider_haydi.webservice.Client
import com.atrau.guider_haydi.App
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SkillModel(var onSkillModelListener: OnSkillModelListener) {
    companion object {
        val TAG = "SkillModel"
    }

    fun getSkillAll() {
        val call: Call<ResponseBody> =
            Client.getService()!!.getSkillAll(App.getMyInsatnce().token)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val skills: ArrayList<Skill> = ArrayList()
                if (response.code() == 200) {
                    //thành cong
                    val jsonObject: JSONObject = JSONObject(response.body()!!.string())
                    val dataArr = jsonObject.getJSONArray("data")

                    for (i in 0 until dataArr.length()) {

                        val obj = dataArr.getJSONObject(i)
                        val id = obj.getInt("id")
                        val name = obj.getString("name")
                        val icon = obj.getString("icon")
                        val desc = obj.getString("desc")
                        Log.d(TAG, "dung... $id $name $icon $desc")
                        val skill: Skill =
                            Skill(id.toInt(), name, icon, desc)
                        skills.add(skill)
                        Log.d(TAG, "get add skill  ")
                    }
                    onSkillModelListener.getSkillSuccess(skills)
                } else {
//                    val jsonObject: JSONObject = JSONObject(response.errorBody()!!.string())
//                    val dataArr = jsonObject.getJSONArray("message")
                    Log.d(TAG, "false...")
                }
            }
        })
    }

    fun putSkills(skills: HashMap<String, ArrayList<HashMap<String, Any>>>) {

        val call: Call<ResponseBody> =
            Client.getService()!!.putSkill(
                App.getMyInsatnce().token,
                skills
            )

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, "onFailure...")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.code() == 200) {

                    Log.d(TAG, "get skill...${skills.toString()}")
                    getSkill(App.getMyInsatnce().token)
                } else {
                    Log.d(
                        TAG,
                        TAG
                    )
//                    Log.d(TAG, "get skill... false  ${response.errorBody()!!.string()}")
//                    getMySkill()
                    Log.d(TAG, "err.......")
                }
            }
        })
    }

    fun getSkill(token: String) {

        val call: Call<ResponseBody> = Client.getService()!!.getSkill(App.getMyInsatnce().token)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {


            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.code() == 200) { //thanh cong

//                    Log.d(TAG,response.body()!!.string()+".....")
                    val jsonObject = JSONObject(response.body()!!.string())
                    val jsonArr = jsonObject.getJSONArray("data")
                    val skills: ArrayList<Skill> = ArrayList()
                    Log.d(TAG, "test: " + response.body()!!.string())
                    for (i in 0 until jsonArr.length()) {
                        val skillJson: JSONObject? = jsonArr.getJSONObject(i)
                        val id: Int = skillJson!!.getInt("id")
                        val name: String = skillJson.getString("name")
                        val level: Double = skillJson.getDouble("level")
                        val skill: Skill = Skill(id, name, level)
                        skills.add(skill)
                        Log.d(TAG, "$id..$name..$level.")
                    }
                    Log.d(TAG, skills.size.toString())
                    onSkillModelListener.getMySkill(skills)
                } else { //that bai


                }
            }
        })
    }
}

