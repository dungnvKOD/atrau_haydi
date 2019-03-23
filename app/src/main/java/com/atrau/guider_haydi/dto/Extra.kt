package com.atrau.guider_haydi.dto

class Extra {
    var id: Int? = null
    var name: String? = null
    var icon: String? = null
    var desc: String? = null
    var key: String? = null

    constructor()

    constructor(id: Int?, name: String?, icon: String?, desc: String?, key: String?) {
        this.id = id
        this.name = name
        this.icon = icon
        this.desc = desc
        this.key = key
    }

    constructor(id: Int?, name: String?, icon: String?, key: String?) {
        this.id = id
        this.name = name
        this.icon = icon
        this.key = key
    }


}