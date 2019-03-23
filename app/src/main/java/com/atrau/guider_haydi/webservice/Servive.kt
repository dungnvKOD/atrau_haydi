package com.atrau.guider_haydi.webservice

import com.atrau.guider_haydi.dto.GuideDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import kotlin.collections.ArrayList
import retrofit2.http.GET


interface Servive {
    // Login register
    @FormUrlEncoded
    @POST("/guider/register")
    fun postRegister(
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("address") address: String,
        @Field("country_code") country_code: String,
        @Field("lat") lat: String,
        @Field("lon") lon: String

    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/guider/login")
    fun postLogin(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Call<ResponseBody>


    @GET("/geo")
    fun getGeo(): Call<ResponseBody>

    @GET("/countries")
    fun getCountries(): Call<ResponseBody>
    //profile

    @GET("/profile")
    fun getProfile(@Header("authorization") token: String): Call<ResponseBody>

    //GET JOB
    @GET("/jobs")
    fun getJob(): Call<ResponseBody>

    //getSkillAll
    @GET("/skills")
    fun getSkillAll(@Header("authorization") token: String): Call<ResponseBody>

    @GET("/my-skills")
    fun getSkill(@Header("authorization") token: String): Call<ResponseBody>

    //putKill
    @PUT("/my-skills")
    fun putSkill(
        @Header("authorization") token: String, @Body skill: HashMap<String, ArrayList<HashMap<String, Any>>>
    ): Call<ResponseBody>


    //putProfile
    @PUT("/profile")
    fun putProfile(@Header("authorization") token: String, @Body guideDto: GuideDto): Call<ResponseBody>


    // get all merchant
    @GET("merchant/guider/orders")
    fun getAllMerchant(@Header("authorization") token: String, @Query("limit") limit: Int, @Query("offset") offset: Int): Call<ResponseBody>

    //get merchant
    @GET("/merchant/guider/orders")
    fun getMerchant(
        @Header("authorization") token: String, @Query("status") status: String
    ): Call<ResponseBody>


    @GET("/order-guider/{order_id}")
    fun getDetailTrip(
        @Header("authorization") token: String,
        @Path("order_id") order_id: String, @Query("payment_type") paymentType: String, @Query(
            "status"
        ) status: String, @Query(
            "cancel_id"
        ) cancelId: Int
    ): Call<ResponseBody>


    // put đong ý yêu cầu


    @PUT("/order-guider/{order_id}")
    fun putDetailTrip(@Header("authorization") token: String, @Path("order_id") order_id: String, @Body body: HashMap<String, String>): Call<ResponseBody>

    // HOST
    @GET("merchant/host/orders")
    fun getHost(
        @Header("authorization") token: String
        , @Query("limit") limit: Int, @Query("offset") offset: Int
    ): Call<ResponseBody>

    //chi tiet host
    @GET("/merchant/host/orders/{order_id}")
    fun getDetailHost(
        @Header("authorization") token: String,
        @Path("order_id") order_id: String, @Query("payment_type") paymentType: String, @Query(
            "status"
        ) status: String, @Query(
            "cancel_id"
        ) cancelId: Int
    ): Call<ResponseBody>

    @GET("settings")
    fun getImage(): Call<ResponseBody>


    // campaigns
    @GET("/campaigns")
    fun getCampaigns(
        @Header("authorization") token: String, @Query("type") type: String, @Query("limit") limit: Int, @Query(
            "offset"
        ) offset: Int
    ): Call<ResponseBody>

    @PUT("/my-jobs")
    fun putJob(@Header("authorization") token: String, @Body hashMap: HashMap<String, ArrayList<Int>>): Call<ResponseBody>




    //  post  payment
    @POST("/payment/request-cashback")
    fun postPayment(@Header("authorization") token: String, @Body body: HashMap<String, Any>): Call<ResponseBody>

    // get  list paymet history
    @GET("/payment/history-payout")
    fun getPaymentHistory(@Header("authorization") token: String, @Query("limit") limit: Int, @Query("offset") offset: Int): Call<ResponseBody>

    //get sum payment
    @GET("/payment/summary")
    fun getSumPayment(@Header("authorization") token: String): Call<ResponseBody>


}