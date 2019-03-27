package com.atrau.guider_haydi.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.dto.Campaign
import com.atrau.guider_haydi.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_campaign.view.*

class CampaignAdapter(val context: Context, val campaigns: ArrayList<Campaign>) :
    RecyclerView.Adapter<CampaignAdapter.MyViewHoder>() {

    private lateinit var paymentOnclickListener: PaymentOnclickListener
    private val inflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoder {
        val view = inflater.inflate(R.layout.item_campaign, parent, false)
        return MyViewHoder(view)
    }

    override fun getItemCount(): Int {
        return campaigns.size
    }

    override fun onBindViewHolder(holder: MyViewHoder, position: Int) {
        val campaign: Campaign = campaigns[holder.adapterPosition]
        Log.d("dung", "link ..." + campaign.images!![0])
        Glide.with(context).load(campaign.images!![0]).into(holder.image)
        holder.itemView.setOnClickListener {
            paymentOnclickListener.onClick(campaign)
        }
    }

    fun updateCampaign(campaign: Campaign) {
        campaigns.add(campaign)
        notifyDataSetChanged()
    }

    inner class MyViewHoder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.img_campaign
    }


    fun setPaymentOnclickListener(paymentOnclickListener: PaymentOnclickListener) {
        this.paymentOnclickListener = paymentOnclickListener

    }

    interface PaymentOnclickListener {
        fun onClick(campaign: Campaign)


    }
}