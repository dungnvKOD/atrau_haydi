package com.atrau.guider_haydi.view.friends

import com.atrau.guider_haydi.dto.Friends

class FriendsPrecenter(var onFriendsViewLisstner: OnFriendsViewLisstner) : OnFriendsLisstener {


    private val friendsModel = FriendsModel(this)

    fun getFriends(limit: Int, offset: Int) {
        friendsModel.getFriends(limit, offset)
    }

    override fun getFriends(arrFrends: ArrayList<Friends>) {
        onFriendsViewLisstner.getFriends(arrFrends)
    }
}