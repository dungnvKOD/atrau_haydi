package com.atrau.guider_haydi.view.friends


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.App

import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.adapter.EndlessRecyclerViewScrollListener
import com.atrau.guider_haydi.adapter.FriendsAdapter
import com.atrau.guider_haydi.conmon.Constant
import com.atrau.guider_haydi.dto.Friends
import com.atrau.guider_haydi.view.HomeActivity
import com.atrau.guider_haydi.view.messagedetail.MessageDetailFragment
import com.atrau.guider_haydi.view.newtrip.NewTripFragment
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.android.synthetic.main.fragment_new_trip.*

class FriendsFragment : Fragment(), OnFriendsViewLisstner, FriendsAdapter.OnClickFriendsListener {

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    companion object {
        val newFragment = FriendsFragment()
    }

    private val friendsPrecenter = FriendsPrecenter(this)
    private var arrFrends: ArrayList<Friends> = ArrayList()
    private lateinit var friendsAdapter: FriendsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @SuppressLint("WrongConstant")
    private fun init() {

        friendsPrecenter.getFriends(10, 0)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rcv_friends.layoutManager = linearLayoutManager
        friendsAdapter = FriendsAdapter(activity!!, arrFrends)
        rcv_friends.adapter = friendsAdapter
        friendsAdapter.setOnClickFriendsListener(this)


        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
//                if (totalItemsCount >= friendsAdapter.getCountItem()) {
//                    return
//                }
                friendsPrecenter.getFriends(10, friendsAdapter.getCountItem())
                Log.d(NewTripFragment.TAG, "onLoadMore ....page= $page limit=$totalItemsCount ")
            }
        }

        rcv_friends.addOnScrollListener(scrollListener)

    }

    override fun getFriends(arrFrends: ArrayList<Friends>) {

        for (i in 0 until arrFrends.size) {
            friendsAdapter.insertFrineds(arrFrends[i])
        }
    }

    override fun onClickMessage(friends: Friends) {
        App.getMyInsatnce().checkMessage = Constant.FRINDS
        App.getMyInsatnce().friends = friends
        (activity as HomeActivity).addOrShowFragment(MessageDetailFragment.newFrament, R.id.layout_kill, true, true)
    }


}
