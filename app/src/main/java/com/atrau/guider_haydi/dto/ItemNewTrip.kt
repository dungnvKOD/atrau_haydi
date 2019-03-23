package com.atrau.guider_haydi.dto

class ItemNewTrip {
    var id: Int? = null
    var startDay: String? = null
    var endDay: String? = null
    var hours: Int? = null
    var customerName: String? = null
    var avatar: String? = null
    var status: String? = null
    var paymentStatus: String? = null

    constructor()
    constructor(id: Int?, startDay: String?, endDay: String?, hours: Int?, customerName: String?, avatar: String?, status: String?, paymentStatus: String?) {

        this.id = id
        this.startDay = startDay
        this.endDay = endDay
        this.hours = hours
        this.customerName = customerName
        this.avatar = avatar
        this.status = status
        this.paymentStatus = paymentStatus
    }

}