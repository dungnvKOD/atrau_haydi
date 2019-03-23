package com.atrau.guider_haydi.view.detailtrip

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atrau.guider_haydi.dto.DetailTrip
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detai_new_tripl.*
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.atrau.guider_haydi.util.MyUtils
import com.atrau.guider_haydi.view.HomeActivity
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.R


@Suppress("PLUGIN_WARNING")
class DetailTripFragment : Fragment(), DetailTripViewListener, View.OnClickListener {

    private lateinit var detailTripPresenter: DetailTripPresenter

    companion object {
        var TAG = "DetailTripFragment"
        var newFragment = DetailTripFragment()
    }

    override fun onStart() {
        super.onStart()

        if (App.getMyInsatnce().tripStatus == "done" || App.getMyInsatnce().tripStatus == "accept") {
            btn_ok_trip.visibility = View.GONE
            btn_cancel.visibility = View.GONE

        }
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_detai_new_tripl, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TOOLBAR
        (activity as HomeActivity).setSupportActionBar(tool_bar_trip_detail)
        (activity as HomeActivity).supportActionBar!!.setDisplayShowTitleEnabled(true)
        tool_bar_trip_detail.navigationIcon = (activity as HomeActivity).resources.getDrawable(
                R.drawable.ic_arrow_back_black_24dp,
                (activity as HomeActivity).theme
        )

        tool_bar_trip_detail.setNavigationOnClickListener {
            (activity as HomeActivity).popbacktask()
        }
        (activity as HomeActivity).title = "CHI TIẾT ĐƠN HÀNG"



        btn_ok_trip.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)

        detailTripPresenter = DetailTripPresenter(this)
        detailTripPresenter.getDetailTrip(App.getMyInsatnce().idTrip.toString())
    }

    // callBack
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getDetailTripSuccess(detailTrip: DetailTrip) {

        setView(detailTrip)
    }

    override fun getDetailTripFailed() {

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setView(detailTrip: DetailTrip) {



        if (detailTrip.avatar == null || detailTrip.avatar == "" || detailTrip.avatar == "null") {
            img_avatar.setImageResource(R.drawable.ic_user)
        } else {

            Glide.with(activity!!).load(detailTrip.avatar).into(img_avatar)
        }
        edt_yeu_cau_nhan_dang.text = detailTrip.note
        txt_name.text = detailTrip.name
        txt_address.text = detailTrip.tripAddress

        btn_call_trip_detail.setOnClickListener {

            if (ContextCompat.checkSelfPermission(
                            activity!!,
                            Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                        activity!!,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        1
                )
            } else {
                call(detailTrip.phone.toString())
            }
        }
        Log.d(TAG, "${detailTrip.startDay.toString()} dung")
        txt_start_day.text =
                MyUtils.convertTime(MyUtils.dateToLong(detailTrip.startDay.toString()), MyUtils.TYPE_DATE_D_M_YYYY)
        txt_ket_thuc.text =
                MyUtils.convertTime(MyUtils.dateToLong(detailTrip.endDay.toString()), MyUtils.TYPE_DATE_D_M_YYYY)
        txt_thoi_gian_hen.text = detailTrip.timeMeet
        txt_dia_diem_hen.text = detailTrip.addressMeet


    }

    private fun call(number: String) {
        Toast.makeText(activity, "dung...", Toast.LENGTH_LONG).show()
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$number")
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (ContextCompat.checkSelfPermission(
                        activity!!,
                        Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
        ) {
            call("22222")
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_ok_trip -> {
                val status: HashMap<String, String> = HashMap()
                status.put("status", "accept")
                Log.d(TAG, status.toString())
                detailTripPresenter.putDatailTrip(status)
            }

            R.id.btn_cancel -> {
                val status: HashMap<String, String> = HashMap()
                status.put("status", "reject")
                detailTripPresenter.putDatailTrip(status)

            }
        }
    }
}
