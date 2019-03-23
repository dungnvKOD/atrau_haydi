package com.atrau.guider_haydi.view.lincome

import com.atrau.guider_haydi.dto.Payment

interface OnPaymentViewListener {


    fun getSumPayment(sum: String)
    fun getHistoryPayment(payments: ArrayList<Payment>, total:Int)
    fun postPaymentFailed(messsage: String)
}