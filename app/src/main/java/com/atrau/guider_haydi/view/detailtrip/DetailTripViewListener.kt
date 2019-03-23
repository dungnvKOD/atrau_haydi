package com.atrau.guider_haydi.view.detailtrip

import com.atrau.guider_haydi.dto.DetailTrip

interface DetailTripViewListener {

    fun getDetailTripSuccess(detailTrip: DetailTrip)
    fun getDetailTripFailed()
}