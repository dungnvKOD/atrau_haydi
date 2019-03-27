package com.atrau.guider_haydi.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.dto.Payment
import com.atrau.guider_haydi.util.MyUtils
import kotlinx.android.synthetic.main.item_payment.view.*


class PaymentAdapter(val context: Context, val payments: ArrayList<Payment>) :
    RecyclerView.Adapter<PaymentAdapter.PaymentViewHodel>() {
    private val inflater = LayoutInflater.from(context)

    companion object {
        val TAG = "PaymentAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHodel {
        val view = inflater.inflate(R.layout.item_payment, parent, false)
        return PaymentViewHodel(view)
    }

    override fun getItemCount(): Int {
        return payments.size
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PaymentViewHodel, position: Int) {
        val payment = payments[holder.adapterPosition]
        holder.txt_day_payment.text =
            MyUtils.convertTime(MyUtils.dateToLong(payment.date.toString()), MyUtils.TYPE_DATE_D_M_YYYY)
        holder.txt_notification_payment.text = payment.message
        if (payment.status == 1) {
            Log.d(TAG, "1....")
            holder.btn_status_payment.text = "Thành công"
            holder.btn_status_payment.setBackgroundResource(R.drawable.shape_btn_finish)
        } else if (payment.status == 2) {
            holder.btn_status_payment.setBackgroundResource(R.drawable.shape_buttom_red)
            holder.btn_status_payment.text = "Từ chối"
        } else if (payment.status == 0) {
            holder.btn_status_payment.text = "Chờ xác nhận"
            holder.btn_status_payment.setBackgroundResource(R.drawable.shape_btn_wating)
        }
        holder.txt_request_payment.text = payment.price.toString()
    }

    fun clearItem() {

        payments.clear()
        notifyDataSetChanged()
    }

    fun updatePaymet(payment: Payment) {
        if (payments.size == 0) {
            payments.add(payment)
        } else {
            payments.add(0, payment)
        }
        notifyDataSetChanged()
    }


    inner class PaymentViewHodel(view: View) : RecyclerView.ViewHolder(view) {
        val txt_day_payment = view.txt_day_payment
        val txt_request_payment = view.txt_request_payment
        val btn_status_payment = view.btn_status_payment
        val txt_notification_payment = view.txt_notification_payment
    }
}