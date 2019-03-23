package com.atrau.guider_haydi.view.newtrip


interface NewTripListener {

    fun getMerchantSuccess(total: Int, listMerchant: ArrayList<Array<Any>>)
    fun getMerchantFailed()

    fun getAllMerchantSuccess(total: Int, listMerchant: ArrayList<Array<Any>>)
    fun getAllMerchantFailed()


}