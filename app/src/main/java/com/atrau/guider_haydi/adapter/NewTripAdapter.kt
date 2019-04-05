package com.atrau.guider_haydi.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.dto.ItemNewTrip
import com.atrau.guider_haydi.util.MyUtils
import com.atrau.guider_haydi.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget


class NewTripAdapter(var context: Context, var items: ArrayList<ItemNewTrip>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {


    companion object {
        var TAG = "NewTripAdapter"
    }

    private var inflater = LayoutInflater.from(context)
    private lateinit var onNewTripListener: OnNewTripListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyNewTripViewHodel {
        val view = inflater.inflate(R.layout.item_new_trip, p0, false)
        return MyNewTripViewHodel(view)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemNewTrip: ItemNewTrip = this.items!![holder.adapterPosition]
        if (holder is MyNewTripViewHodel) {

            holder.avatar.setImageResource(R.drawable.ic_user)
            if (itemNewTrip.avatar == "" || itemNewTrip.avatar == null || itemNewTrip.avatar == "null") {
                holder.avatar.setImageResource(R.drawable.ic_user)
            } else {
                Glide.with(context.applicationContext).load(itemNewTrip.avatar).apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                )
                    .into(object : SimpleTarget<Drawable>(200, 200) {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
                        ) {

                            holder.avatar.setImageDrawable(resource)
                        }
                    })
            }


            holder.hour.text = itemNewTrip.hours.toString()+" Ngày"
            holder.name.text = itemNewTrip.customerName.toString()
            holder.time.text =
                " ${MyUtils.convertTime(
                    MyUtils.dateToLong(itemNewTrip.startDay.toString()),
                    MyUtils.TYPE_DATE_D_M_YYYY
                )}  - ${MyUtils.convertTime(
                    MyUtils.dateToLong(itemNewTrip.endDay.toString()),
                    MyUtils.TYPE_DATE_D_M_YYYY
                )}"

            when {
                itemNewTrip.status == "new" -> {
                    holder.btn.setBackgroundResource(R.drawable.shape_btn_wating)
                    holder.btn.text = "Chờ xác nhận"
                    holder.itemView.setOnClickListener {
                        onNewTripListener.newListener(itemNewTrip.paymentStatus.toString(), itemNewTrip.id!!, "new")
                    }
                    Log.d(TAG, "new $position")
                }
                itemNewTrip.status == "cancel" -> {
                    holder.btn.setBackgroundResource(R.drawable.shape_red)
                    holder.btn.text = "Đã hủy"
                    holder.itemView.setOnClickListener {
                        onNewTripListener.newListener(itemNewTrip.paymentStatus.toString(), itemNewTrip.id!!, "done")

                    }
                    Log.d(TAG, "cancel $position")
                }
                itemNewTrip.status == "done" -> {
                    holder.btn.setBackgroundResource(R.drawable.shape_done)
                    holder.btn.text = "Hoàn thành"
                    holder.itemView.setOnClickListener {
                        onNewTripListener.newListener(itemNewTrip.paymentStatus.toString(), itemNewTrip.id!!, "done")

                    }
                    Log.d(TAG, "done $position")
                }
                itemNewTrip.status == "reject" -> {
                    holder.btn.setBackgroundResource(R.drawable.shape_red)
                    holder.btn.text = "Từ chối"
                    Log.d(TAG, "tu choi $position")
                    holder.itemView.setOnClickListener {
                        onNewTripListener.newListener(itemNewTrip.paymentStatus.toString(), itemNewTrip.id!!, "reject")

                    }
                }
                itemNewTrip.status == "accept" -> {
                    holder.btn.setBackgroundResource(R.drawable.shape_btn_wating)
                    holder.btn.text = "Chấp nhận"
                    holder.itemView.setOnClickListener {
                        onNewTripListener.newListener(itemNewTrip.paymentStatus.toString(), itemNewTrip.id!!, "accept")

                    }
                    Log.d(TAG, "accept $position")
                }
            }
        }
    }


    fun updateItem(itemsUpdate: ItemNewTrip) {
        Log.d("adapter", "   dung123")
        this.items!!.add(itemsUpdate)
        notifyDataSetChanged()
    }

    fun cleanItem() {
        this.items!!.clear()
        notifyDataSetChanged()
    }

    fun setOnClickListener(onNewTripListener: OnNewTripListener) {
        this.onNewTripListener = onNewTripListener
    }

    interface OnNewTripListener {
        fun newListener(paymentType: String, id: Int, tripStatus: String)

    }
}

class MyNewTripViewHodel(view: View) : RecyclerView.ViewHolder(view) {
    val avatar: ImageView = view.findViewById(R.id.img_avatar)
    val name: TextView = view.findViewById(R.id.txt_name_trip)
    var hour: TextView = view.findViewById(R.id.txt_time_trip)
    var time: TextView = view.findViewById(R.id.txt_time_day_trip)
    var btn: Button = view.findViewById(R.id.btn_buttom_new_trip)

}