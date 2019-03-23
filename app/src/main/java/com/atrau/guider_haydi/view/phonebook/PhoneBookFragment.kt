package com.atrau.guider_haydi.view.phonebook


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.view.message_group.MessageFragment

class PhoneBookFragment : Fragment() {

    companion object {
        val newFragment= PhoneBookFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_book, container, false)
    }


}
