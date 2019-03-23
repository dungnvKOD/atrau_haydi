package com.atrau.guider_haydi.view.message_group


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.view.phonebook.PhoneBookFragment
import kotlinx.android.synthetic.main.fragment_message_group.*


class MessageGroupFragment : Fragment() {

    companion object {
        var newFragment = MessageGroupFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        val adapter = MyFragmentPagerAdapter(childFragmentManager)
        view_pager!!.adapter = adapter
        tab_layout!!.setupWithViewPager(view_pager)

    }


    inner class MyFragmentPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        private val titles = arrayOf("Tin Nhắn", "Danh Bạ")


        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {

                    MessageFragment.newFragment
                }
                1 -> {

                    PhoneBookFragment.newFragment
                }
                else -> {

                    MessageFragment.newFragment
                }
            }
        }

        override fun getCount(): Int {
            return titles.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> {
                    titles[0]
                }
                1 -> {
                    titles[1]
                }
                else -> {
                    titles[0]
                }

            }
        }
    }

}
