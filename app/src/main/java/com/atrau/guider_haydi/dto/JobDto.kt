package com.atrau.guider_haydi.dto

class JobDto {

    var id: Int? = null
    var name: String? = null
    var nameEn: String? = null
    var icon: String? = null

    constructor(id: Int?, name: String?, nameEn: String?, icon: String?) {
        this.id = id
        this.name = name
        this.nameEn = nameEn
        this.icon = icon
    }

    constructor()
}