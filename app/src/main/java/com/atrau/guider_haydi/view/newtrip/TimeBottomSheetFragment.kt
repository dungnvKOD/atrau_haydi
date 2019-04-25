package com.atrau.guider_haydi.view.newtrip


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.util.MyUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_time_bottom_sheet.*
import java.util.*


class TimeBottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {


    private lateinit var rootView: View
    private var datePickerTo: Long = -1
    private var datePickerFrom: Long = -1
    private lateinit var onClickListener: OnClickListener

    companion object {
        @SuppressLint("StaticFieldLeak")
        val newFragment = TimeBottomSheetFragment()
        val TAG = "TimeBottomSheetFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_time_bottom_sheet, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        datePickerFrom = MyUtils().timeHere()
        datePickerTo = MyUtils().timeHere()

        txt_to.text = MyUtils.convertTime(MyUtils().timeHere(), MyUtils.TYPE_DATE_D_M_YYYY)
        txt_from.text = MyUtils.convertTime(MyUtils().timeHere(), MyUtils.TYPE_DATE_D_M_YYYY)

        txt_from.setOnClickListener(this)
        btn_apply.setOnClickListener(this)
        txt_to.setOnClickListener(this)
        btn_skip.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.txt_from -> {
                val calendar: Calendar = Calendar.getInstance()
                val datePickerDialogFrom: DatePickerDialog =
                    DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        txt_from.text = "$dayOfMonth-${month+1}-$year"
                        datePickerFrom = calendar.timeInMillis
                        Log.d(TAG, "dung.......${calendar.timeInMillis}")
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                datePickerDialogFrom.show()

            }

            R.id.txt_to -> {
                val calendar: Calendar = Calendar.getInstance()
                val datePickerDialogTo: DatePickerDialog =
                    DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        datePickerTo = calendar.timeInMillis
                        txt_to.text = "$dayOfMonth-${month+1}-$year"

                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                datePickerDialogTo.show()
            }

            R.id.btn_apply -> {
                Log.d(TAG, "from: " + datePickerFrom + " to : " + datePickerTo)
                if (datePickerFrom <= datePickerTo) {
                    onClickListener.onClickTime(datePickerFrom, datePickerTo)

                } else {
                    Toast.makeText(activity, "Chọn lại thời gian", Toast.LENGTH_LONG).show()
                }
            }

            R.id.btn_skip -> {
                dismiss()
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClickTime(timeFrom: Long, timeTo: Long)
    }
}