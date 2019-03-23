package com.atrau.guider_haydi.dto


class Campaign {

    var id: Int? = null
    var images: ArrayList<String>? = null
    var link: String? = null
    var name: String? = null
    var desc: String? = null
    var status: Int? = null
    var isSponsor: Int? = null
    var updatedAt: String? = null
    var type: String? = null

    constructor()
    constructor(
        id: Int?,
        link: String?,
        name: String?,
        desc: String?,
        status: Int?,
        isSponsor: Int?,
        updatedAt: String?,
        type: String?,
        images: ArrayList<String>?
    ) {
        this.id = id
        this.link = link
        this.name = name
        this.desc = desc
        this.status = status
        this.isSponsor = isSponsor
        this.updatedAt = updatedAt
        this.type = type
        this.images = images
    }


}