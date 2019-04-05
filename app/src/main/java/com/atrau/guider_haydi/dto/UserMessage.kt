package com.atrau.guider_haydi.dto

class UserMessage {
    var id = -1
    var avatar: String? = null
    var created_at: String? = null
    var name: String? = null
    var nickname: String? = null
    var type: String? = null


    constructor(id: Int, avatar: String?, created_at: String, name: String?, nickname: String?, type: String?) {
        this.id = id
        this.avatar = avatar
        this.created_at = created_at
        this.name = name
        this.nickname = nickname
        this.type = type
    }

    constructor()
}