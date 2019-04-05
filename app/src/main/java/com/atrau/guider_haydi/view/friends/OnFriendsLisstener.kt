package com.atrau.guider_haydi.view.friends

import com.atrau.guider_haydi.dto.Friends

interface OnFriendsLisstener {
    fun getFriends(arrFrends: ArrayList<Friends>)
}