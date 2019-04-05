package com.atrau.guider_haydi.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.dto.Conversations
import com.atrau.guider_haydi.util.MyUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import kotlinx.android.synthetic.main.item_conversations.view.*

class ConversationsAdapter(val context: Context, var arrConversations: ArrayList<Conversations>) :
    RecyclerView.Adapter<ConversationsAdapter.MyViewHoder>() {

    private lateinit var onClickLisstener: OnClickLisstener
    val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoder {

        val view = inflater.inflate(R.layout.item_conversations, parent, false)
        return MyViewHoder(view)
    }

    override fun getItemCount(): Int {
        return arrConversations.size
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MyViewHoder, position: Int) {
        val conversations = arrConversations[holder.adapterPosition]

        if (conversations.receiver!!.id == App.getMyInsatnce().guideDto.id!!.toInt()) {
            holder.image.setImageResource(R.drawable.ic_user)
            Glide.with(context!!).load(conversations.sender!!.avatar).apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
            ).into(object : SimpleTarget<Drawable>(200, 200) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
                ) {

                    holder.image.setImageDrawable(resource)
                }
            })

            if (arrConversations.size >= position) {
                holder.time.visibility = View.GONE
            }

            holder.name.text = conversations.sender!!.name
            holder.message.text = conversations.lastMessage
            holder.time.text = MyUtils().getRelativeTimeSpanString(context, conversations.updateAt)

        } else {
            holder.image.setImageResource(R.drawable.ic_user)
            Glide.with(context!!).load(conversations.receiver!!.avatar).apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
            ).into(object : SimpleTarget<Drawable>(200, 200) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
                ) {
                    holder.image.setImageDrawable(resource)
                }
            })

            holder.name.text = conversations.receiver!!.name
            holder.message.text = conversations.lastMessage
            holder.time.text = MyUtils().getRelativeTimeSpanString(context, conversations.updateAt)
            holder.itemView.setOnClickListener {
                onClickLisstener.onClickItem(conversations)
            }
        }
    }

    fun setConversations(conversations: Conversations) {
        arrConversations.add(0, conversations)
        notifyDataSetChanged()

    }

    fun updateItem(conversations: Conversations) {
        for (i in 0 until arrConversations.size) {
            if (arrConversations[i].room == conversations.room) {
                arrConversations.removeAt(i)
                arrConversations.add(0, conversations)
                notifyDataSetChanged()
            }
        }
    }

    fun setOnClickListener(onClickLisstener: OnClickLisstener) {
        this.onClickLisstener = onClickLisstener

    }

    inner class MyViewHoder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.img_avatar_cv
        val name: TextView = view.txt_name_user
        val message: TextView = view.txt_sub_message
        val time: TextView = view.txt_time_message
    }

    interface OnClickLisstener {
        fun onClickItem(conversations: Conversations)

    }


}