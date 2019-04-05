package com.atrau.guider_haydi.view.message_group

import com.atrau.guider_haydi.dto.Conversations

class MessagePresenter(val onViewMessageListener: OnViewMessageListener) : OnMessageListener {


    val messageModel: MessageModel = MessageModel(this)
    fun getConversations() {
        messageModel.getConversations()

    }

    override fun getConversations(conversations: Conversations) {
        onViewMessageListener.getConversations(conversations)
    }

    override fun updateConversations(conversations: Conversations) {
        onViewMessageListener.updateConversations(conversations)
    }
}