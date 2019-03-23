package com.atrau.guider_haydi.view.campaign

import android.util.Log
import com.atrau.guider_haydi.webservice.Client
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.dto.Campaign
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CampaignsModel(val campaignListener: CampaignListener) {
    companion object {
        var TAG = "CampaignsModel"
    }

    fun getCampaigns(offset: Int, limit: Int) {

        val call: Call<ResponseBody> =
            Client.getService()!!.getCampaigns(App.getMyInsatnce().token, "merchant", limit, offset)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {


                val arrayList: ArrayList<Campaign> = ArrayList()
                if (response.code() == 200) { //thanh cong
                    val objJson = JSONObject(response.body()!!.string())
                    var jsonArray = objJson.getJSONArray("data")

                    for (i in 0 until jsonArray.length()) {
                        var jsonOBJ = jsonArray.getJSONObject(i)

                        val id = jsonOBJ.getInt("id")
                        val link = jsonOBJ.getString("link")
                        val name = jsonOBJ.getString("name")
                        val desc = jsonOBJ.getString("desc")
                        val isSponsor = jsonOBJ.getInt("is_sponsor")
                        val updatedAt = jsonOBJ.getString("updated_at")
                        val type = jsonOBJ.getString("type")
                        val status = jsonOBJ.getInt("status")

                        Log.d("dung", "linkModel=$link name $name,$desc")

                        val imageArr: ArrayList<String> = ArrayList()
                        val images = jsonOBJ.getJSONArray("images")
                        for (i in 0 until images.length()) {
                            Log.d("dung", "linkModel=" + images[i])
                            imageArr.add(images[i] as String)
                        }

                        val campaign: Campaign =
                            Campaign(
                                id,
                                link,
                                name,
                                desc,
                                status,
                                isSponsor,
                                updatedAt,
                                type,
                                imageArr
                            )
                        arrayList.add(campaign)
                    }
                    campaignListener.getCampaign(arrayList)
                } else { //that bai
                }
            }
        })
    }

}