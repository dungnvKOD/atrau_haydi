package com.atrau.guider_haydi.view.campaign

import com.atrau.guider_haydi.dto.Campaign


interface CampaignViewListener {

    fun getCampaign(campaigns: ArrayList<Campaign>)

}