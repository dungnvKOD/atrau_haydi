package com.atrau.guider_haydi.view.message_group

import com.atrau.guider_haydi.dto.Conversations

interface OnViewMessageListener {
    fun getConversations(conversations: Conversations)
    fun updateConversations(conversations: Conversations)
}