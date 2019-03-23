package com.atrau.guider_haydi.view.detailtrip


import com.atrau.guider_haydi.dto.DetailTrip

import java.util.HashMap

class DetailTripPresenter(val detailViewTripListener: DetailTripViewListener) :
    DetailTripListener {

    private val detailTripModel = DetailTripModel(this)


    fun getDetailTrip(id: String) {
        detailTripModel.getDetailTrip(id)


    }

    fun putDatailTrip(status: HashMap<String, String>) {
        detailTripModel.putDetailTrip(status)

    }

    override fun getDetailTripSuccess(detailTrip: DetailTrip) {
        detailViewTripListener.getDetailTripSuccess(detailTrip)
    }

    override fun getDetailTripFailed() {
        detailViewTripListener.getDetailTripFailed()
    }
}