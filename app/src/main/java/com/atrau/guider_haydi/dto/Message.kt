package com.atrau.guider_haydi.dto

class Message {

    var content: String? = null
    var updateAt: Long = 1L
    var receiver: UserMessage? = null
    var sender: UserMessage? = null
    var type: String? = null


    constructor(
        content: String?,
        updateAt: Long,
        receiver: UserMessage?,
        sender: UserMessage?,
        type: String?
    ) {
        this.content = content

        this.updateAt = updateAt
        this.receiver = receiver
        this.sender = sender
        this.type = type
    }

    constructor()
}