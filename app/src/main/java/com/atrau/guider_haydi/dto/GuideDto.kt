package com.atrau.guider_haydi.dto

import com.google.gson.annotations.SerializedName

class GuideDto {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("phone")
    var phone: String? = null
    @SerializedName("facebook_id")
    var facebookId: String? = null
    @SerializedName("password")
    var password: String? = null
    @SerializedName("source")
    var source: String? = null
    @SerializedName("email")
    var email: String? = null
    @SerializedName("sub_phone")
    var subPhone: String? = null
    @SerializedName("address")
    var address: String? = null
    @SerializedName("country_code")
    var countryCode: String? = null
    @SerializedName("level")
    var level: String? = null
    @SerializedName("status")
    var status: Int? = null
    @SerializedName("type")
    var type: String? = null
    @SerializedName("rate")
    var rate: String? = null
    @SerializedName("vote_total")
    var voteTotal: Int? = null
    @SerializedName("order_total")
    var orderTotal: Int? = null
    @SerializedName("languages")
    var languages: String? = null
    @SerializedName("quote")
    var quote: String? = null
    @SerializedName("my_value")
    var myValue: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("price")
    var price: Int? = null
    @SerializedName("unit")
    var priceUnit: String? = null
    @SerializedName("lat")
    var lat: String? = null
    @SerializedName("lon")
    var lon: String? = null
    @SerializedName("cover")
    var cover: String? = null
    @SerializedName("avatar")
    var avatar: String? = null
    @SerializedName("created_at")
    var createdAt: String? = null
    @SerializedName("updated_at")
    var updatedAt: String? = null
    var jobs: String? = null

    constructor()

    constructor(
        id: String?,
        name: String?,
        phone: String?,
        facebookId: String?,
        password: String?,
        source: String?,
        email: String?,
        subPhone: String?,
        address: String?,
        countryCode: String?,
        level: String?,
        status: Int?,
        type: String?,
        rate: String?,
        voteTotal: Int?,
        orderTotal: Int?,
        languages: String?,
        quote: String?,
        myValue: String?,
        description: String?,
        price: Int?,
        priceUnit: String?,
        lat: String?,
        lon: String?,
        cover: String?,
        avatar: String?,
        createdAt: String?,
        updatedAt: String?,
        jobs: String?
    ) {
        this.id = id
        this.name = name
        this.phone = phone
        this.facebookId = facebookId
        this.password = password
        this.source = source
        this.email = email
        this.subPhone = subPhone
        this.address = address
        this.countryCode = countryCode
        this.level = level
        this.status = status
        this.type = type
        this.rate = rate
        this.voteTotal = voteTotal
        this.orderTotal = orderTotal
        this.languages = languages
        this.quote = quote
        this.myValue = myValue
        this.description = description
        this.price = price
        this.priceUnit = priceUnit
        this.lat = lat
        this.lon = lon
        this.cover = cover
        this.avatar = avatar
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.jobs = jobs
    }

    constructor(
        email: String?,
        subPhone: String?,
        address: String?,
        quote: String?,
        myValue: String?,
        description: String?,
        price: Int?,
        priceUnit: String?,
        cover: String?,
        avatar: String?,
        languages: String?

    ) {

        this.email = email
        this.subPhone = subPhone
        this.address = address
        this.quote = quote
        this.myValue = myValue
        this.description = description
        this.price = price
        this.priceUnit = priceUnit
        this.cover = cover
        this.avatar = avatar
        this.languages = languages

    }

}