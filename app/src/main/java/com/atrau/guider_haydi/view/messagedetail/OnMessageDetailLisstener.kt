package com.atrau.guider_haydi.view.messagedetail

import com.atrau.guider_haydi.dto.Message

interface OnMessageDetailLisstener {

    fun getMessage(mesage: Message)
    fun senMessageFailure()
    fun senMessageSuccess()

}