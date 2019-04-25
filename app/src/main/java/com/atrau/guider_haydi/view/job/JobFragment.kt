package com.atrau.guider_haydi.view.job


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.adapter.JobAdapter
import com.atrau.guider_haydi.conmon.Constant
import com.atrau.guider_haydi.view.HomeActivity
import kotlinx.android.synthetic.main.fragment_job.*


class JobFragment : Fragment(), JobViewLestener {

    private lateinit var onClickListener: OnClickListener
    private val jobPresenter = JobPresenter(this)
    private lateinit var jobAdapter: JobAdapter

    companion object {
        val TAG = "JobFragment"
        val newFragment = JobFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        (activity as HomeActivity).setSupportActionBar(toolbar_add_job)
        (activity as HomeActivity).supportActionBar!!.setDisplayShowTitleEnabled(true)
        toolbar_add_job.navigationIcon = (activity as HomeActivity).resources.getDrawable(
            R.drawable.ic_keyboard_backspace_black_24dp,
            (activity as HomeActivity).theme
        )

        toolbar_add_job.setNavigationOnClickListener {
            fillJob()
        }

        //adapter

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rcv_job.layoutManager = linearLayoutManager
        jobAdapter = JobAdapter(activity!!, (activity as HomeActivity).arrJob)
        rcv_job.adapter = jobAdapter
    }

    private fun fillJob() {
        val ids: ArrayList<Int> = ArrayList()
        for (i in 0 until (activity as HomeActivity).arrJob.size) {
            if ((activity as HomeActivity).arrJob[i].isEmpty) {
                ids.add((activity as HomeActivity).arrJob[i].id!!)
            }
        }

        val hashMap: HashMap<String, ArrayList<Int>> = HashMap()
        hashMap.put("jobs", ids)
        jobPresenter.putJob(hashMap, Constant.JOB)
    }


    override fun putJob() {

        onClickListener.onClickItem()
        (activity as HomeActivity).popbacktask()
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClickItem()

    }

}
