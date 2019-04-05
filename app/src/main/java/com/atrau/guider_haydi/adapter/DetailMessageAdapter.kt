package com.atrau.guider_haydi.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.dto.Conversations
import com.atrau.guider_haydi.dto.Message
import com.atrau.guider_haydi.util.MyUtils
import com.bumptech.glide.Glide

import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.message_left.view.*
import kotlinx.android.synthetic.main.message_right.view.*


class DetailMessageAdapter(val context: Context, val mes: ArrayList<Message>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onClickLisstener: OnClickLisstenerDetail
    private lateinit var onClickListener: OnClickListener
    val inflater = LayoutInflater.from(context)
    val ITEM_TYPE_LEFT = 0
    val ITEM_TYPE_RIGHT = 1

    companion object {
        val TAG = "DetailMessageAdapter"

    }

    override fun getItemViewType(position: Int): Int {

        return if (mes!![position].sender!!.id != App.getMyInsatnce().guideDto.id!!.toInt()) {
            ITEM_TYPE_LEFT
        } else {
            ITEM_TYPE_RIGHT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_RIGHT) {
            val view: View = inflater.inflate(R.layout.message_right, parent, false)

            ItemViewHolderRight(view)

        } else {
            val view: View = inflater.inflate(R.layout.message_left, parent, false)

            ItemViewHolderLeft(view)
        }


    }

    override fun getItemCount(): Int {
        return mes!!.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = mes!![position]
        onClickListener.onFocusItem(holder.adapterPosition)

        if (holder is ItemViewHolderRight) {

            if (mes.size-1 == holder.adapterPosition) {
                holder.txtTimeMessage.visibility = View.VISIBLE
            }else{
                holder.txtTimeMessage.visibility = View.GONE
            }
            holder.txtTimeMessage.text = MyUtils().getRelativeTimeSpanString(context, message.updateAt)

            holder.txtItemMessageRight.text = message.content



        } else if (holder is ItemViewHolderLeft) {
            if (mes.size-1 == holder.adapterPosition) {
                holder.txtTimeMessage.visibility = View.VISIBLE
            }else{
                holder.txtTimeMessage.visibility = View.GONE
            }

            Glide.with(context).load(message.receiver!!.avatar).into(holder.imgMyAvatar)
            holder.txtTimeMessage.text = MyUtils().getRelativeTimeSpanString(context, message.updateAt)
            holder.txtItemMessageLeft.text = message.content

        }


    }


    fun insertMessage(message: Message) {
        mes!!.add(message)
        notifyItemInserted(mes!!.size - 1)
    }


    class ItemViewHolderLeft(view: View) : RecyclerView.ViewHolder(view) {
        val imgMyAvatar: CircleImageView = view.imgMyAvatarLeft
        val txtItemMessageLeft: TextView = view.txtItemMessageLeft
//        val imgItemImageLeft: ImageView = view.imgItemImageLeft
        val txtTimeMessage: TextView = view.txtTimeMessage
    }

    class ItemViewHolderRight(view: View) : RecyclerView.ViewHolder(view) {
        //        val imgMyAvatar: CircleImageView = view.imgMyAvatarRight
        val txtItemMessageRight: TextView = view.txtItemMessageRight
//        val imgItemImageRight: ImageView = view.imgItemImageRight
        val txtTimeMessage: TextView = view.txtTimeMessageRight
    }

    private fun getImage(url: String, img: ImageView) {
        Glide.with(context)
            .load(url)
            .into(img)

    }

    fun setOnClickItem(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener

    }

    interface OnClickListener {
        fun onFocusItem(psistion: Int) {

        }
    }


    fun setOnClickListener(onClickLisstener: OnClickLisstenerDetail) {
        this.onClickLisstener = onClickLisstener

    }

    interface OnClickLisstenerDetail {
        fun onFocusItem(position: Int)

    }


}