package com.atrau.guider_haydi.view.messagedetail


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.atrau.guider_haydi.App

import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.adapter.DetailMessageAdapter
import com.atrau.guider_haydi.conmon.Constant
import com.atrau.guider_haydi.dto.Conversations
import com.atrau.guider_haydi.dto.Message
import com.atrau.guider_haydi.dto.UserMessage
import com.atrau.guider_haydi.util.MyUtils
import com.atrau.guider_haydi.view.HomeActivity
import com.atrau.guider_haydi.view.message_group.MessagePresenter
import com.atrau.guider_haydi.view.profile.ProfileFragment
import kotlinx.android.synthetic.main.fragment_message_detail.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


class MessageDetailFragment : Fragment(), View.OnClickListener, OnMessageViewListener,
    DetailMessageAdapter.OnClickLisstenerDetail, DetailMessageAdapter.OnClickListener {


    private var messageDetailPresenter: MessageDetailPresenter = MessageDetailPresenter(this)
    private lateinit var detailMessageAdapter: DetailMessageAdapter
    private lateinit var mess: ArrayList<Message>
    private lateinit var messagePresenter: MessagePresenter

    companion object {

        val TAG = "MessageDetailFragment"
        val newFrament = MessageDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("WrongConstant")
    private fun initView() {

        //TITLE
        (activity as HomeActivity).setSupportActionBar(toolbar_message_detail)
        (activity as HomeActivity).supportActionBar!!.setDisplayShowTitleEnabled(true)
        toolbar_message_detail.navigationIcon = (activity as HomeActivity).resources.getDrawable(
            R.drawable.ic_arrow_back_black_24dp,
            (activity as HomeActivity).theme
        )
        toolbar_message_detail.setNavigationOnClickListener {
            messageDetailPresenter.remove()
            (activity as HomeActivity).popbacktask()
        }


        if (App.getMyInsatnce().checkMessage == Constant.FRINDS) {
            //Neu từ friends sang
            (activity as HomeActivity).title = App.getMyInsatnce().friends.name
            var room = ""
            room = if (App.getMyInsatnce().guideDto.id!!.toInt() >= App.getMyInsatnce().friends.id!!) {
                "${App.getMyInsatnce().friends.id}_${App.getMyInsatnce().guideDto.id}"
            } else {

                "${App.getMyInsatnce().guideDto.id}_${App.getMyInsatnce().friends.id}"
            }


            messageDetailPresenter.getMessage(room)
        } else {
            //Neu từ message sang
            if (App.getMyInsatnce().conversations.receiver!!.id == App.getMyInsatnce().guideDto.id!!.toInt()) {
                (activity as HomeActivity).title = App.getMyInsatnce().conversations.sender!!.name
            } else {
                (activity as HomeActivity).title = App.getMyInsatnce().conversations.receiver!!.name

            }

            messageDetailPresenter.getMessage(App.getMyInsatnce().conversations.room!!)
        }

        rcv_detail_message.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rcv_detail_message.layoutManager = linearLayoutManager
        linearLayoutManager.stackFromEnd = true
        detailMessageAdapter =
            DetailMessageAdapter(
                activity!!,
                ArrayList<Message>()
            )

        rcv_detail_message.adapter = detailMessageAdapter
        detailMessageAdapter.setOnClickItem(this)
        detailMessageAdapter.setOnClickListener(this)
        detailMessageAdapter.setOnClickListener(this)
        btn_sent.setOnClickListener(this)
        image.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_sent -> {

                val textMessage = edt_input.text.toString().trim()
                if (textMessage == "") {
                    return
                }
                val type: String = Constant.TEXT
                sentMessage(textMessage, type)

            }
            R.id.image -> {
                checkPermisstion()
            }
        }
    }


    private fun sentMessage(textMessage: String, type: String) {
        if (App.getMyInsatnce().checkMessage == Constant.CONVERSATIONS) {
            val mesage =
                Message(
                    textMessage,

                    MyUtils().timeHere(),
                    App.getMyInsatnce().conversations.receiver,
                    App.getMyInsatnce().conversations.sender,
                    type
                )
            val conversations = App.getMyInsatnce().conversations
            conversations.updateAt = MyUtils().timeHere()
            messageDetailPresenter.insertMessage(mesage, conversations)
        } else {

            var room = ""
            room = if (App.getMyInsatnce().guideDto.id!!.toInt() >= App.getMyInsatnce().friends.id!!) {
                "${App.getMyInsatnce().friends.id}_${App.getMyInsatnce().guideDto.id}"
            } else {
                "${App.getMyInsatnce().guideDto.id}_${App.getMyInsatnce().friends.id}"
            }

            val sender: UserMessage = UserMessage(
                App.getMyInsatnce().guideDto.id!!.toInt(),
                "abcc",
                App.getMyInsatnce().guideDto.createdAt!!,
                App.getMyInsatnce().guideDto.name,
                App.getMyInsatnce().guideDto.name, type
            )

            val receiver: UserMessage = UserMessage(
                App.getMyInsatnce().friends.id!!,
                App.getMyInsatnce().friends.avatar,
                App.getMyInsatnce().friends.createdAt!!,
                App.getMyInsatnce().friends.name,
                App.getMyInsatnce().friends.nickname,
                type
            )


            val mesage =
                Message(
                    textMessage,

                    MyUtils().timeHere(),
                    receiver,
                    sender,
                    "text"
                )
            val conversations = Conversations(textMessage, room, MyUtils().timeHere(), receiver, sender)
            messageDetailPresenter.insertMessage(mesage, conversations)
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun onIsPermisstionNotGranted() {
        //chua dc cap dc cap
        requestPermissions(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            Constant.REQESS_IMAGE
        )
    }

    private fun checkPermisstion() {
        if ((activity as HomeActivity).hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            openImage()
            Toast.makeText(activity, "da dc cap quyên", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(activity, "chua dc cap quyen", Toast.LENGTH_LONG).show()
            onIsPermisstionNotGranted()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constant.REQESS_IMAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //da dc cap
                openImage()
            }
        }
    }

    private fun openImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, Constant.REQESS_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val image = data.data
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap((activity as HomeActivity).contentResolver, image)

            persistImage(bitmap).toString()

            val type: String = Constant.IMAGE
            sentMessage(persistImage(bitmap).toString(), type)

        }
    }

    private fun persistImage(bitmap: Bitmap): File? {
        val filesDir: File = (activity as HomeActivity).filesDir
        val currentTime: Date = Calendar.getInstance().time
        val name: String = "$currentTime"
        val imageFile: File = File(filesDir, "$name.jpg")
        val os: OutputStream
        return try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
            imageFile
        } catch (e: Exception) {
            Log.d(ProfileFragment.TAG, "dung...", e)
            null
        }
    }


    override fun getMessage(mesage: Message) {
        detailMessageAdapter.insertMessage(mesage)
        Log.d(TAG, "getMessage...")
    }

    override fun onFocusItem(position: Int) {
        rcv_detail_message.smoothScrollToPosition(position)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        messageDetailPresenter.remove()
    }

    override fun senMessageFailure() {
        MyUtils().hideKeyboard(activity!!)
    }

    override fun senMessageSuccess() {
        edt_input.setText("")
        MyUtils().hideKeyboard(activity!!)

    }


}
