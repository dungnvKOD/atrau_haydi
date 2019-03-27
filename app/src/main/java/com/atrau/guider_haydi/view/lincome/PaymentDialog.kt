package com.atrau.guider_haydi.view.lincome


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.atrau.guider_haydi.base.BaseDialog
import com.atrau.guider_haydi.R
import kotlinx.android.synthetic.main.payment_dialog.*


class PaymentDialog(context: Context, var many: String, var dialogListener: PaymentDialogListener) :
    BaseDialog(context), View.OnClickListener {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            super.onCreate(savedInstanceState)
        }
        setContentView(R.layout.payment_dialog)

        txt_sum_many.setText("Tổng tiền : " + many)

        btn_cancel_many.setOnClickListener(this)
        btn_many.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.btn_cancel_many -> {
                cancel()

            }
            R.id.btn_many -> {
                val many = edt_payment.text.toString().trim()
                if (many == "") {
                    Toast.makeText(context, "Chưa nhập số tiền", Toast.LENGTH_LONG).show()
                    return
                }
                if (many.toInt() > this.many.toInt()) {
                    Toast.makeText(context, "Số tiền rút không đủ", Toast.LENGTH_LONG).show()
                } else {
                    dialogListener.onSkillListener(many)
                }


            }
        }
    }


    interface PaymentDialogListener {
        fun onSkillListener(many: String)
    }

}