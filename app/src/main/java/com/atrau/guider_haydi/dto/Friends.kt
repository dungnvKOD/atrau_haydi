package com.atrau.guider_haydi.dto

class Friends {
    var name: String? = null
    var id: Int? = null
    var avatar: String? = null
    var nickname: String? = null
    var type: String? = null
    var createdAt: String? = null

    constructor(name: String?, id: Int?, avatar: String?, nickname: String?, type: String?, createdAt: String?) {
        this.name = name
        this.id = id
        this.avatar = avatar
        this.nickname = nickname
        this.type = type
        this.createdAt = createdAt
    }

    constructor()


}