package com.atrau.guider_haydi.dto

import com.google.gson.annotations.SerializedName

class Skill {
    @SerializedName("skill_id")
    var id: Int? = null
    var name: String? = null
    var level: Double = 0.0
    var icon: String? = null
    var desc: String? = null


    constructor()

    constructor(id: Int?, name: String?, level: Double) {
        this.id = id
        this.name = name
        this.level = level
    }

    constructor(name: String?, level: Double) {
        this.name = name
        this.level = level
    }

    constructor(id: Int?, name: String?, level: Double, icon: String?, desc: String?) {
        this.id = id
        this.name = name
        this.level = level
        this.icon = icon
        this.desc = desc
    }

    constructor(id: Int, name: String?, icon: String?, desc: String?) {
        this.id = id
        this.name = name
        this.icon = icon
        this.desc = desc
    }

    constructor(id: Int?, level: Double) {
        this.id = id
        this.level = level
    }


}