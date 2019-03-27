package com.atrau.guider_haydi.view.lincome


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.atrau.guider_haydi.adapter.PaymentAdapter
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.adapter.EndlessRecyclerViewScrollListener
import com.atrau.guider_haydi.dto.Payment
import com.atrau.guider_haydi.dto.Skill
import com.atrau.guider_haydi.view.HomeActivity
import com.atrau.guider_haydi.view.skill.AddSkill
import kotlinx.android.synthetic.main.fragment_campaign.*
import kotlinx.android.synthetic.main.fragment_income.*

class LincomeFragment : Fragment(), OnPaymentViewListener, View.OnClickListener {


    private var total: Int = 0
    private var limit: Int = 0
    private var status = ""
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private lateinit var paymentAdapter: PaymentAdapter
    private var payments: ArrayList<Payment> = ArrayList()
    private val paymentPresenter = PaymentPresenter(this)
    private val RUT_TIEN = "RUT_TIEN"
    private var check: String = ""
    private lateinit var dialog: PaymentDialog

    companion object {
        val TAG = "LincomeFragment"
        var newFrgament = LincomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_income, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("WrongConstant")
    private fun initView() {

        //Lấy giá ở đây
        paymentPresenter.getSumPayment()
        paymentPresenter.getPaymentHistory(0, 0)
//        paymentPresenter.postPayment()

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rcv_payment.layoutManager = linearLayoutManager
        paymentAdapter = PaymentAdapter(activity!!, payments)
        rcv_payment.adapter = paymentAdapter

        btn_rut_tien.setOnClickListener(this)

        //        LẮNG NGHE SỰ KIỆN SCOLL
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (totalItemsCount >= total) {
                    return
                }
                paymentPresenter.getPaymentHistory(10, totalItemsCount)

            }
        }
        rcv_payment.addOnScrollListener(scrollListener)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_rut_tien -> {


                dialog = PaymentDialog(activity as HomeActivity,
                    txt_sum_payment.text.toString(),
                    @SuppressLint("ValidFragment")
                    object : PaymentDialog.PaymentDialogListener {
                        override fun onSkillListener(many: String) {
                            Toast.makeText(activity, "btn", Toast.LENGTH_LONG).show()
                            val hashMap: HashMap<String, Any> = HashMap()
                            hashMap.put("money", txt_sum_payment.text)
                            hashMap.put("unit", "VND")
                            paymentPresenter.postPayment(hashMap)
                            check = RUT_TIEN
                            //đến đây mói tắt dialog
                            dialog.cancel()
                        }
                    })
                dialog.show()

            }
        }
    }


    override fun getSumPayment(sum: String) {
        if (sum == "null") {
            txt_sum_payment.text = "0"
        }
        txt_sum_payment.text = sum
    }

    override fun getHistoryPayment(payments: ArrayList<Payment>, total: Int) {
        this.total = total
        Toast.makeText(activity, "ok..", Toast.LENGTH_LONG).show()
        if (check == RUT_TIEN) {
//            this.total = total
            paymentAdapter.clearItem()
        }

        for (i in 0 until payments.size) {
            val price = payments[i].price
            val status = payments[i].status
            val date = payments[i].date
            val priceUnit = payments[i].priceUnit
            val payment = Payment(price, priceUnit, status, date)
            paymentAdapter.updatePaymet(payment)
        }
        Log.d(TAG, "$total")

        paymentPresenter.getSumPayment()
    }

    override fun postPaymentFailed(messsage: String) {
        Toast.makeText(activity, messsage, Toast.LENGTH_LONG).show()
    }
}
