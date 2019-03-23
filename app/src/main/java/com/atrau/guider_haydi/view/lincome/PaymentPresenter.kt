package com.atrau.guider_haydi.view.lincome

import com.atrau.guider_haydi.dto.Payment


class PaymentPresenter(val onPaymentViewListener: OnPaymentViewListener) :
    OnPaymentListener {
    private val paymentModel = PaymentModel(this)

    fun postPayment(body: HashMap<String, Any>) {
        paymentModel.postPayment(body)

    }

    fun getPaymentHistory(limit: Int, offset: Int) {
        paymentModel.getPaymentHistory(limit, offset)
    }


    override fun postPaymentFailed(messsage: String) {
        onPaymentViewListener.postPaymentFailed(messsage)
    }

    fun getSumPayment() {
        paymentModel.getSumPayment()
    }

    override fun getSumPayment(sum: String) {
        onPaymentViewListener.getSumPayment(sum)
    }

    override fun getHistoryPayment(payments: ArrayList<Payment>, total:Int) {
        onPaymentViewListener.getHistoryPayment(payments,total)
    }

}