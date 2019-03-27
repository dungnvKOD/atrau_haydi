package com.atrau.guider_haydi.view.campaign

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.adapter.CampaignAdapter
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.adapter.EndlessRecyclerViewScrollListener
import com.atrau.guider_haydi.dto.Campaign
import com.atrau.guider_haydi.view.HomeActivity
import kotlinx.android.synthetic.main.fragment_campaign.*


class CampaignFragment : Fragment(), CampaignViewListener, CampaignAdapter.PaymentOnclickListener {


    private var total: Int = 0
    private var limit: Int = 0
    private var status = ""
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var campaigns: ArrayList<Campaign> = ArrayList()
    private lateinit var campaignsAdapter: CampaignAdapter

    companion object {
        val TAG = "CampaignFragment"
        var newFragment = CampaignFragment()
    }

    private lateinit var campaignPresenter: CampaignPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_campaign, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        campaignPresenter = CampaignPresenter(this)
        campaignPresenter.getCampaignsModel(0, 5)

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rcv_campaigns.layoutManager = linearLayoutManager
        campaignsAdapter = CampaignAdapter(activity!!, campaigns)
        rcv_campaigns.adapter = campaignsAdapter

        //        LẮNG NGHE SỰ KIỆN SCOLL
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (totalItemsCount >= total) {
                    return
                }
                campaignPresenter.getCampaignsModel(totalItemsCount, 5)

            }
        }
        rcv_campaigns.addOnScrollListener(scrollListener)
        campaignsAdapter.setPaymentOnclickListener(this)
    }

    override fun getCampaign(campaigns: ArrayList<Campaign>) {
        this.total = total
        for (i in 0 until campaigns.size) {
            val campaign: Campaign = campaigns[i]
            Log.d("dung", "link=" + campaign.link)
            campaignsAdapter.updateCampaign(campaign)

        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onClick(campaign: Campaign) {
        (activity as HomeActivity).campaign = campaign
        (activity as HomeActivity).addOrShowFragment(FragmentWebViewFragment.newFragment, R.id.layout_kill, true, true)

    }

}
