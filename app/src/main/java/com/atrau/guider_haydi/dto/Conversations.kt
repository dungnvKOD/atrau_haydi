package com.atrau.guider_haydi.dto

class Conversations {
    var lastMessage: String? = null
    var room: String? = null
    var updateAt: Long = 1L
    var receiver: UserMessage? = null
    var sender: UserMessage? = null



    constructor(lastMessage: String?, room: String?, updateAt: Long, receiver: UserMessage?, sender: UserMessage?) {
        this.lastMessage = lastMessage
        this.room = room
        this.updateAt = updateAt
        this.receiver = receiver
        this.sender = sender
    }

    constructor()
}