package com.atrau.guider_haydi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.R

class ServiceAdapter(val context: Context, val services: ArrayList<String>) : RecyclerView.Adapter<ServiceAdapter.MyNewTripViewHodel>() {
    private val inference = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewTripViewHodel {
        val view = inference.inflate(R.layout.item_service, parent, false)
        return MyNewTripViewHodel(view)
    }

    override fun getItemCount(): Int {
        return services.size
    }

    override fun onBindViewHolder(holder: MyNewTripViewHodel, position: Int) {
        val service = services[holder.adapterPosition]
        holder.item.text = service


    }


    inner class MyNewTripViewHodel(view: View) : RecyclerView.ViewHolder(view) {
        val item: TextView = view.findViewById(R.id.txt_service)

    }
}