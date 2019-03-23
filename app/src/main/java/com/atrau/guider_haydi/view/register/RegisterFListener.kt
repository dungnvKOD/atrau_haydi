package com.atrau.guider_haydi.view.register

interface RegisterFListener {

    fun registerSuccess(id: String, name: String, phone: String, token: String)
    fun registerFale(message: String)

    fun getCountriesSuccess(array: MutableList<MutableList<String>>)
    fun getCountriesFaile()

}