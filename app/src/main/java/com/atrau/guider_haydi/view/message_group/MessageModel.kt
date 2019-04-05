package com.atrau.guider_haydi.view.message_group

import android.util.Log
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.dto.Conversations
import com.atrau.guider_haydi.view.profile.ProfileModel
import com.atrau.guider_haydi.webservice.Client
import com.google.firebase.database.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageModel(val onMessageListener: OnMessageListener) {

    private val reference = FirebaseDatabase.getInstance().reference

    companion object {
        val TAG = "MessageModel"
    }

    fun getConversations() {

        val childEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val conversations: Conversations = p0.getValue(Conversations::class.java)!!
                onMessageListener.updateConversations(conversations)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val conversations: Conversations = p0.getValue(Conversations::class.java)!!
                onMessageListener.getConversations(conversations)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        }
        reference.child("conversations").child(App.getMyInsatnce().guideDto.id!!).orderByChild("updateAt")
            .addChildEventListener(childEventListener)
    }
}