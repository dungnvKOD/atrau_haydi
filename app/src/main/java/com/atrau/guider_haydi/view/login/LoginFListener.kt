package com.atrau.guider_haydi.view.login

interface LoginFListener {
    fun loginSuccess()
    fun loginFale(message: String)
    fun getGeoSuccess(country: String, region: String, eu: String, timezone: String, city: String, lat: Double, lon: Double, metro: String, area: String)
    fun getGeoFaile()

    fun getImageSuccess(banner:String)
}