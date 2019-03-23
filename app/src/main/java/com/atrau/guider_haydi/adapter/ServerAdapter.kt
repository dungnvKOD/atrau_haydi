package com.atrau.guider_haydi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.R

class ServerAdapter(val context: Context, val service: ArrayList<String>) : RecyclerView.Adapter<ServerAdapter.MyServiceViewHodel>() {

    val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyServiceViewHodel {
        val view = inflater.inflate(R.layout.item_service, parent, false)
        return MyServiceViewHodel(view)

    }

    override fun getItemCount(): Int {
        return service.size
    }

    override fun onBindViewHolder(holder: MyServiceViewHodel, position: Int) {

    }

    inner class MyServiceViewHodel(view: View) : RecyclerView.ViewHolder(view) {
        val txt_service: TextView = view.findViewById(R.id.txt_service)

    }


}