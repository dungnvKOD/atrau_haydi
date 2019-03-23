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

import com.atrau.guider_haydi.adapter.PaymentAdapter
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.dto.Payment
import kotlinx.android.synthetic.main.fragment_income.*

class LincomeFragment : Fragment(), OnPaymentViewListener, View.OnClickListener {


    private lateinit var paymentAdapter: PaymentAdapter
    private var payments: ArrayList<Payment> = ArrayList()
    private val paymentPresenter = PaymentPresenter(this)
    private val RUT_TIEN = "RUT_TIEN"
    private var check: String = ""

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
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_rut_tien -> {
                Toast.makeText(activity, "btn", Toast.LENGTH_LONG).show()
                val hashMap: HashMap<String, Any> = HashMap()
                hashMap.put("money", txt_sum_payment.text)
                hashMap.put("unit", "VND")
                paymentPresenter.postPayment(hashMap)
                check = RUT_TIEN
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

        if (check == RUT_TIEN) {
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


    }

    override fun postPaymentFailed(messsage: String) {
        Toast.makeText(activity, messsage, Toast.LENGTH_LONG).show()
    }


}
