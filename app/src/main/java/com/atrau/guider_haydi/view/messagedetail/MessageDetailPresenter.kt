package com.atrau.guider_haydi.view.messagedetail

import com.atrau.guider_haydi.dto.Conversations
import com.atrau.guider_haydi.dto.Message
import com.google.firebase.database.ChildEventListener

class MessageDetailPresenter(var onMessageViewListener: OnMessageViewListener) : OnMessageDetailLisstener {


    var messageDetailModel = MessageDetailModel(this)
    fun getMessage(room: String) {
        messageDetailModel.getMessage(room)

    }

    fun remove() {
        messageDetailModel.remove()
    }


    fun insertMessage(message: Message, conversations: Conversations) {
        messageDetailModel.insertMessage(message, conversations)
    }

    override fun getMessage(mesage: Message) {
        onMessageViewListener.getMessage(mesage)
    }

    override fun senMessageFailure() {

        onMessageViewListener.senMessageFailure()
    }

    override fun senMessageSuccess() {
        onMessageViewListener.senMessageSuccess()
    }

}