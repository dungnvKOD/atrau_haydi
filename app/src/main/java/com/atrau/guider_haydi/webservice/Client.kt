package com.atrau.guider_haydi.webservice


import com.atrau.guider_haydi.conmon.Constant
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory;

class Client {
    //viet phuong thuc lay data ve

    companion object {
        private var service: Servive? = null
        fun getService(): Servive? {
            if (service == null) {
                Client()//de khoi tao sevice
            }
            return service
        }
    }

    init {

        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)//quy dinh thoi gian client conect den server,qua thoi gian la huy
            .readTimeout(10, TimeUnit.SECONDS)//thoi gian cho du lieu ve

        val builder = Retrofit.Builder()
            .baseUrl(Constant.URL_API) //chi dinh phan dau api la gi
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create()) //dung gson
        service = builder.build().create(Servive::class.java)
    }

//    fun checkError(data: String?): Boolean {
//        val jsonObj: JSONObject = JSONObject(data)
//        val error = jsonObj.getBoolean("error")
//        return error
//    }

}