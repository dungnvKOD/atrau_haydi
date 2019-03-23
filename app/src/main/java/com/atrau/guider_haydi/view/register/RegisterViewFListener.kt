package com.atrau.guider_haydi.view.register

interface RegisterViewFListener {

    fun nameIsEmpty()
    fun emailIsEmpty()
    fun emailInvalid()
    fun phoneIsEmpty()
    fun passwordIsEmpty()
    fun phoneInvalid()
    fun passwordShort()
    fun nameInvalid()
    fun registerSuccess(id: String, name: String, phone: String, token: String)
    fun registerFale(message: String)

    fun getCountriesSuccess(array: MutableList<MutableList<String>>)
    fun getCountriesFaile()

}