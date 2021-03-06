package com.atrau.guider_haydi.view.newtrip

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager

import com.atrau.guider_haydi.adapter.NewTripAdapter
import com.atrau.guider_haydi.dto.ItemNewTrip
import com.atrau.guider_haydi.view.HomeActivity
import com.atrau.guider_haydi.view.detailtrip.DetailTripFragment
import kotlinx.android.synthetic.main.fragment_new_trip.*

import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.adapter.EndlessRecyclerViewScrollListener
import com.atrau.guider_haydi.util.MyUtils


class NewTripFragment : Fragment(),
    NewTripViewListener, NewTripAdapter.OnNewTripListener, View.OnClickListener,
    TimeBottomSheetFragment.OnClickListener {


    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    var itemNewTrips: ArrayList<ItemNewTrip>? = ArrayList()
    private lateinit var newTripAdapter: NewTripAdapter
    private var total: Int = 0
    private var limit: Int = 0
    private var status = ""
    private lateinit var timeFragment: TimeBottomSheetFragment


    companion object {
        val TAG = "NewTripFragment"
        var newFragment = NewTripFragment()
        private val DONE = "done"
        private val NEW = "new"
        private val CANCEL = "reject"

    }

    private val newTripPresenter: NewTripPresenter =
        NewTripPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_trip, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).setSupportActionBar(tool_bar_trip)
        (activity as HomeActivity).supportActionBar!!.setDisplayShowTitleEnabled(true)
        (activity as HomeActivity).title = ""
        init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newTripPresenter.getAllMerchant(5, 0)
        newTripAdapter = NewTripAdapter(activity!!, itemNewTrips)
        newTripAdapter.cleanItem()
        //        LẮNG NGHE SỰ KIỆN SCOLL


    }

    // KHỞI TẠO
    private fun init() {
        timeFragment = TimeBottomSheetFragment.newFragment
        timeFragment.setOnClickListener(this)
        val linearLayoutManager = LinearLayoutManager(activity)
        rcv_new_trip.layoutManager = linearLayoutManager
        rcv_new_trip.adapter = newTripAdapter
        newTripAdapter.setOnClickListener(this)

        //KHỎI TẠO ITEM ĐẦU


        txt_view_finish.setOnClickListener(this)
        txt_wating.setOnClickListener(this)
        txt_view_cancel.setOnClickListener(this)
        setting.setOnClickListener(this)
        txt_view_all.setOnClickListener(this)

        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (totalItemsCount >= total) {
                    return
                }
                newTripPresenter.getAllMerchant(4, totalItemsCount)
            }
        }

        rcv_new_trip.addOnScrollListener(scrollListener)
    }


    override fun onStart() {
        super.onStart()

    }

    override fun getMerchantSuccess(total: Int, listMerchant: ArrayList<Array<Any>>) {
        this.total = total
        if (status == DONE || status == NEW || status == CANCEL) {
            newTripAdapter.cleanItem()
//            Log.d(TAG, "clear... : " )

        }

        Log.d(TAG, listMerchant.size.toString())
        for (i in 0 until listMerchant.size) {
//            Log.d("adapter", i.toString() + "size")
            val startDay = listMerchant[i][0].toString()
            val endDay = listMerchant[i][1].toString()
            val hours = listMerchant[i][2]
            val customerName = listMerchant[i][3].toString()
            val avatar = listMerchant[i][4].toString()
            val status = listMerchant[i][5].toString()
            val paymentStatus = listMerchant[i][6].toString()
            val id = listMerchant[i][7].toString()
//            Log.d(TAG, "dung all... : " + status+ "  "+paymentStatus)
            val itemNewTrip: ItemNewTrip =
                ItemNewTrip(
                    id.toInt(),
                    startDay,
                    endDay,
                    hours as Int,
                    customerName,
                    avatar,
                    status,
                    paymentStatus
                )
            newTripAdapter.updateItem(itemNewTrip)
        }
    }

    override fun getMerchantFailed() {

    }


    fun popBackTask(){

        MyUtils().hideKeyboard(activity!!)
        (activity as HomeActivity).popbacktask()
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.txt_view_finish -> {
                status = DONE
                newTripAdapter.cleanItem()

                txt_view_finish.setTextColor(this.resources.getColor(R.color.blule))
                txt_wating.setTextColor(this.resources.getColor(R.color.black))
                txt_view_cancel.setTextColor(this.resources.getColor(R.color.black))
                txt_view_all.setTextColor(this.resources.getColor(R.color.black))
                newTripPresenter.getMerchant("done")
            }
            R.id.txt_wating -> {
                status = NEW
                newTripAdapter.cleanItem()

                txt_view_finish.setTextColor(this.resources.getColor(R.color.black))
                txt_wating.setTextColor(this.resources.getColor(R.color.blule))
                txt_view_cancel.setTextColor(this.resources.getColor(R.color.black))
                txt_view_all.setTextColor(this.resources.getColor(R.color.black))
                newTripPresenter.getMerchant("new")
            }

            R.id.txt_view_cancel -> {
                status = CANCEL
                newTripAdapter.cleanItem()

                txt_view_finish.setTextColor(this.resources.getColor(R.color.black))
                txt_wating.setTextColor(this.resources.getColor(R.color.black))
                txt_view_cancel.setTextColor(this.resources.getColor(R.color.blule))
                txt_view_all.setTextColor(this.resources.getColor(R.color.black))
                newTripPresenter.getMerchant("reject")
            }
            R.id.setting -> {
                val timeFragment = TimeBottomSheetFragment.newFragment
                timeFragment.show(childFragmentManager, timeFragment.javaClass.name)
            }
            R.id.txt_view_all -> {
                status = ""
                newTripPresenter.getAllMerchant(5, 0)
                newTripAdapter.cleanItem()
                txt_view_finish.setTextColor(this.resources.getColor(R.color.black))
                txt_wating.setTextColor(this.resources.getColor(R.color.black))
                txt_view_cancel.setTextColor(this.resources.getColor(R.color.black))
                txt_view_all.setTextColor(this.resources.getColor(R.color.blule))

            }
        }
    }

    override fun getAllMerchantSuccess(total: Int, listMerchant: ArrayList<Array<Any>>) {
        this.total = total
        if (status == DONE || status == NEW || status == CANCEL) {
            newTripAdapter.cleanItem()

        }
        Log.d(TAG, listMerchant.size.toString())
        for (i in 0 until listMerchant.size) {
            val startDay = listMerchant[i][0].toString()
            val endDay = listMerchant[i][1].toString()
            val hours = listMerchant[i][2]
            val customerName = listMerchant[i][3].toString()
            val avatar = listMerchant[i][4].toString()
            val status = listMerchant[i][5].toString()
            val paymentStatus = listMerchant[i][6].toString()
            val id = listMerchant[i][7].toString()
            val itemNewTrip: ItemNewTrip =
                ItemNewTrip(
                    id.toInt(),
                    startDay,
                    endDay,
                    hours as Int,
                    customerName,
                    avatar,
                    status,
                    paymentStatus
                )
            newTripAdapter.updateItem(itemNewTrip)
        }
        Log.d(TAG, "${itemNewTrips!!.size}  ..." + listMerchant.size)
    }

    override fun getAllMerchantFailed() {

    }

    override fun newListener(paymentType: String, id: Int, tripStatus: String) {
        App.getMyInsatnce().idTrip = id
        App.getMyInsatnce().tripStatus = tripStatus
        (activity as HomeActivity).addOrShowFragment(DetailTripFragment.newFragment, R.id.layout_kill, true, true)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClickTime(timeFrom: Long, timeTo: Long) {
        newTripAdapter.cleanItem()
        newTripPresenter.getAllMerchant(5, 0)
        val newTripTime: ArrayList<ItemNewTrip> = ArrayList()
        Log.d(TAG,itemNewTrips!!.size.toString())

        for (i in 0 until itemNewTrips!!.size) {
            if (MyUtils.dateToLong(itemNewTrips!![i].startDay!!) in timeFrom..timeTo) {
                newTripAdapter.updateItem(itemNewTrips!![i])
            }
        }

        timeFragment.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
//        (activity as HomeActivity).popbacktask()
    }
}





