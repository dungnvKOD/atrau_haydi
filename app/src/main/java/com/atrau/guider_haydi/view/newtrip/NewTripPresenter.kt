package com.atrau.guider_haydi.view.newtrip

class NewTripPresenter(val newTripViewListener: NewTripViewListener) :
    NewTripListener {


    val newTripModel: NewTripModel = NewTripModel(this)

    fun getAllMerchant(limit: Int, offset: Int) {
        newTripModel.getAllMerchant(limit, offset)

    }

    fun getMerchant(status: String) {
        newTripModel.getMerchant(status)

    }

    override fun getMerchantSuccess(total: Int, listMerchant: ArrayList<Array<Any>>) {
        newTripViewListener.getMerchantSuccess(total, listMerchant)
    }

    override fun getMerchantFailed() {

    }

    override fun getAllMerchantSuccess(total: Int, listMerchant: ArrayList<Array<Any>>) {
        newTripViewListener.getAllMerchantSuccess(total, listMerchant)
    }

    override fun getAllMerchantFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}