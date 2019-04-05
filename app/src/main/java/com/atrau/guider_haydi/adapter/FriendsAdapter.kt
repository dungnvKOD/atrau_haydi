package com.atrau.guider_haydi.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.dto.Friends
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import kotlinx.android.synthetic.main.icon_friends.view.*

class FriendsAdapter(var context: Context, var arrFriends: ArrayList<Friends>) :
    RecyclerView.Adapter<FriendsAdapter.MyViewHodel>() {
    private lateinit var onClickFriendsListener: OnClickFriendsListener
    private val inflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHodel {
        val view = inflater.inflate(R.layout.icon_friends, parent, false)
        return MyViewHodel(view)
    }

    override fun getItemCount(): Int {
        return arrFriends.size
    }

    override fun onBindViewHolder(holder: MyViewHodel, position: Int) {
        val friends = arrFriends[holder.adapterPosition]
//        Glide.with(context).load(friends.avatar).into(holder.img)

        holder.img.setImageResource(R.drawable.ic_user)
        Glide.with(context).load(friends.avatar).apply(
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
        ).into(object : SimpleTarget<Drawable>(200, 200) {
            override fun onResourceReady(
                resource: Drawable,
                transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
            ) {

                holder.img.setImageDrawable(resource)
            }
        })

        holder.name.text = friends.name

        holder.itemView.setOnClickListener {
            onClickFriendsListener.onClickMessage(friends)
        }
    }

    fun getCountItem(): Int {

        return arrFriends.size
    }


    fun insertFrineds(friends: Friends) {
        this.arrFriends.add(friends)
        notifyItemInserted(0)
    }

    inner class MyViewHodel(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.img_avatar_friends
        var name: TextView = view.txt_name_friends

    }

    fun setOnClickFriendsListener(onClickFriendsListener: OnClickFriendsListener) {
        this.onClickFriendsListener = onClickFriendsListener
    }

    interface OnClickFriendsListener {
        fun onClickMessage(friends: Friends)

    }

}