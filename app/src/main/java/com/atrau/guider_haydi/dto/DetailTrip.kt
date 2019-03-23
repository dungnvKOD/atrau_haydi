package com.atrau.guider_haydi.dto

import com.google.gson.annotations.SerializedName

class DetailTrip {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("user_id")
    var userId: Int? = null
    @SerializedName("trip_id")
    var tripId: Int? = null
    @SerializedName("guider_id")
    var guiderId: Int? = null
    @SerializedName("start_day")
    var startDay: String? = null
    @SerializedName("end_day")
    var endDay: String? = null
    @SerializedName("hours")
    var hours: Int? = null
    @SerializedName("time_meet")
    var timeMeet: String? = null
    @SerializedName("address_meet")
    var addressMeet: String? = null
    @SerializedName("lat")
    var lat: String? = null
    @SerializedName("lon")
    var lon: String? = null
    @SerializedName("note")
    var note: String? = null
    @SerializedName("money")
    var money: Int? = null
    @SerializedName("total_money")
    var totalMoney: String? = null
    @SerializedName("payment_status")
    var paymentStatus: String? = null
    @SerializedName("payment_type")
    var paymentType: String? = null
    @SerializedName("online")
    var online: String? = null
    @SerializedName("status")
    var status: String? = null
    @SerializedName("cancel_id")
    var cancelId: String? = null
    @SerializedName("created_at")
    var createdAt: String? = null
    @SerializedName("updated_at")
    var updatedAt: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("avatar")
    var avatar: String? = null
    @SerializedName("rate")
    var rate: String? = null
    @SerializedName("vote_total")
    var voteTotal: Int? = null
    @SerializedName("phone")
    var phone: String? = null
    @SerializedName("email")
    var email: String? = null
    @SerializedName("trip_address")
    var tripAddress: String? = null
    @SerializedName("trip_start_day")
    var tripStartDay: String? = null
    @SerializedName("trip_end_day")
    var tripEndDay: String? = null
    @SerializedName("peoples")
    var peoples: Int? = null

    constructor()
    constructor(id: Int?, userId: Int?, tripId: Int?, guiderId: Int?, startDay: String?, endDay: String?, hours: Int?, timeMeet: String?, addressMeet: String?, lat: String?, lon: String?, note: String?, money: Int?, totalMoney: String?, paymentStatus: String?, paymentType: String?, online: String?, status: String?, cancelId: String?, createdAt: String?, updatedAt: String?, name: String?, avatar: String?, rate: String?, voteTotal: Int?, phone: String?, email: String?, tripAddress: String?, tripStartDay: String?, tripEndDay: String?, peoples: Int?) {
        this.id = id
        this.userId = userId
        this.tripId = tripId
        this.guiderId = guiderId
        this.startDay = startDay
        this.endDay = endDay
        this.hours = hours
        this.timeMeet = timeMeet
        this.addressMeet = addressMeet
        this.lat = lat
        this.lon = lon
        this.note = note
        this.money = money
        this.totalMoney = totalMoney
        this.paymentStatus = paymentStatus
        this.paymentType = paymentType
        this.online = online
        this.status = status
        this.cancelId = cancelId
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.name = name
        this.avatar = avatar
        this.rate = rate
        this.voteTotal = voteTotal
        this.phone = phone
        this.email = email
        this.tripAddress = tripAddress
        this.tripStartDay = tripStartDay
        this.tripEndDay = tripEndDay
        this.peoples = peoples
    }

}