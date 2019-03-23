package com.atrau.guider_haydi.view.campaign

import com.atrau.guider_haydi.dto.Campaign

class CampaignPresenter(private val campaignViewListener: CampaignViewListener) :
    CampaignListener {


    private val campaignsModel = CampaignsModel(this)

    fun getCampaignsModel(offset: Int, limit: Int) {
        campaignsModel.getCampaigns(offset, limit)

    }

    override fun getCampaign(campaigns: ArrayList<Campaign>) {
        campaignViewListener.getCampaign(campaigns)
    }

}