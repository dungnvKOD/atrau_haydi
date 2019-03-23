package com.atrau.guider_haydi.dto

class Payment {
    var price: Int? = null
    var priceUnit: String? = null
    var status: Int? = null
    var message: String? = null
    var date:String?=null

    constructor()
    constructor(price: Int?, priceUnit: String?, status: Int?, message: String?, date: String?) {
        this.price = price
        this.priceUnit = priceUnit
        this.status = status
        this.message = message
        this.date = date
    }

    constructor(price: Int?, priceUnit: String?, status: Int?, date: String?) {
        this.price = price
        this.priceUnit = priceUnit
        this.status = status
        this.date = date
    }


}