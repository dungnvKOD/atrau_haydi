package com.atrau.guider_haydi.view.messagedetail

import android.net.Uri
import android.util.Log
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.dto.Conversations
import com.atrau.guider_haydi.dto.Message
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MessageDetailModel(var onMessageDetailLisstener: OnMessageDetailLisstener) {
    companion object {
        var TAG = "MessageDetailModel"
    }

    private val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val storageReference: StorageReference = FirebaseStorage.getInstance().reference
    private lateinit var childEventListener: ChildEventListener
    private var room: String = ""
    private lateinit var valueEventListener: ValueEventListener
    private var checkInsert: Boolean = false


    fun insertMessage(message: Message, conversations: Conversations) {
        checkInsert = true


        var node = ""
        node = if (message.sender!!.id >= message.receiver!!.id) {
            "${message.receiver!!.id}_${message.sender!!.id}"
        } else {

            "${message.sender!!.id}_${message.receiver!!.id}"
        }

        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["lastMessage"] = message.content!!
        hashMap["updateAt"] = message.updateAt


        valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var check = 0


                for (data: DataSnapshot in p0.child(App.getMyInsatnce().guideDto.id!!).children) {

                    if (data.child("room").value == conversations.room) {
                        val nodeUser = data.key
                        if (nodeUser != null) {
                            Log.d(TAG, "nodeUser...")
                            reference.child("conversations").child(App.getMyInsatnce().guideDto.id!!)
                                .child(nodeUser).updateChildren(hashMap).addOnSuccessListener {
                                    onMessageDetailLisstener.senMessageSuccess()

                                }
                                .addOnFailureListener {
                                    onMessageDetailLisstener.senMessageFailure()

                                }
                            return

                        }
                    }

                    check++
                }
                //kierm tra xem user nay cs tin nhan hay chuw nau chu thi tao du n
                if (!p0.child(App.getMyInsatnce().guideDto.id!!).exists()) {
                    reference.child("conversations").child(App.getMyInsatnce().guideDto.id!!)
                        .push().setValue(conversations).addOnSuccessListener {
                            onMessageDetailLisstener.senMessageSuccess()

                        }
                        .addOnFailureListener {
                            onMessageDetailLisstener.senMessageFailure()

                        }
                    return
                }
                //
                if (check >= p0.child(App.getMyInsatnce().guideDto.id!!).childrenCount) {
                    reference.child("conversations").child(App.getMyInsatnce().guideDto.id!!)
                        .push().setValue(conversations).addOnSuccessListener {
                            onMessageDetailLisstener.senMessageSuccess()

                        }
                        .addOnFailureListener {
                            onMessageDetailLisstener.senMessageFailure()

                        }
                    return
                }
            }
        }


        reference.child("messages").child(node).push().setValue(message)

        reference.child("conversations")
            .addValueEventListener(valueEventListener)
    }

    private fun insertImage(message: String, node: String) {
//        if (message != null) {
//            Log.d(TAG, "${message} ...")
//            val line = storageReference.child("Messaged").child(node).putFile(Uri.parse(message))
//
//            line.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    storageReference.child("Messaged").child(node).downloadUrl.addOnSuccessListener { uri: Uri? ->
//                        message.image = uri.toString()
//                        Log.d(TAG, "${message}...link")
//                        /**
//                         *
//                         *  day data len database
//                         *
//                         */
//
//                        Log.d(TAG, "${message}...S")
//
//                        reference.child("Messaged").child(node).push().setValue(message)
//                        reference.child("Messaged").child(node).updateChildren(hashMap)
//                        message.image = null
//                        onMessageFModelListener.senMessagerSuccess()
//
//                    }.addOnFailureListener {
//                        message.image = null
//                        Log.d(TAG, "${message.image}...F")
//                        onMessageFModelListener.senMessagerFailed()
//                    }
//                }
//            }
        }

        fun getMessage(room: String) {
            this.room = room
            childEventListener = object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    val message: Message = p0.getValue(Message::class.java)!!
                    onMessageDetailLisstener.getMessage(message)

                }

                override fun onChildRemoved(p0: DataSnapshot) {
                }
            }

            reference.child("messages").child(room)
                .addChildEventListener(childEventListener)
        }

        fun remove() {
            if (checkInsert) {
                reference.child("conversations")
                    .removeEventListener(valueEventListener)
                checkInsert = false
            }

            reference.child("messages").child(room)
                .removeEventListener(childEventListener)
        }
    }