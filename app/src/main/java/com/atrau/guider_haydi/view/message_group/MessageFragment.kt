package com.atrau.guider_haydi.view.message_group


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.atrau.guider_haydi.App

import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.adapter.ConversationsAdapter
import com.atrau.guider_haydi.conmon.Constant
import com.atrau.guider_haydi.dto.Conversations
import com.atrau.guider_haydi.view.HomeActivity
import com.atrau.guider_haydi.view.messagedetail.MessageDetailFragment
import kotlinx.android.synthetic.main.fragment_message.*

class MessageFragment : Fragment(), OnViewMessageListener, ConversationsAdapter.OnClickLisstener {


    private val messagePresenter = MessagePresenter(this)
    private lateinit var conversationsAdapter: ConversationsAdapter

    companion object {
        val newFragment = MessageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initView()
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        messagePresenter.getConversations()
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rcv_conversations.layoutManager = linearLayoutManager
        conversationsAdapter = ConversationsAdapter(activity!!, ArrayList<Conversations>())
        rcv_conversations.adapter = conversationsAdapter
        conversationsAdapter.setOnClickListener(this)

    }

    override fun getConversations(conversations: Conversations) {
        conversationsAdapter.setConversations(conversations)


    }

    override fun onClickItem(conversations: Conversations) {
        App.getMyInsatnce().checkMessage = Constant.CONVERSATIONS
        App.getMyInsatnce().conversations = conversations
        (activity as HomeActivity).addOrShowFragment(MessageDetailFragment.newFrament, R.id.layout_kill, true, true)
    }

    override fun updateConversations(conversations: Conversations) {
        conversationsAdapter.updateItem(conversations)

    }


}
