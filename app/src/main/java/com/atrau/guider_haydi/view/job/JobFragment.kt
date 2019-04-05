package com.atrau.guider_haydi.view.job


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.adapter.JobAdapter
import com.atrau.guider_haydi.dto.JobDto
import com.atrau.guider_haydi.view.HomeActivity
import kotlinx.android.synthetic.main.fragment_job.*

class JobFragment : Fragment(), JobViewLestener {

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
        jobPresenter.getJob()

        (activity as HomeActivity).setSupportActionBar(toolbar_add_job)
        (activity as HomeActivity).supportActionBar!!.setDisplayShowTitleEnabled(true)
        toolbar_add_job.navigationIcon = (activity as HomeActivity).resources.getDrawable(
            R.drawable.ic_arrow_back_black_24dp,
            (activity as HomeActivity).theme
        )

        toolbar_add_job.setNavigationOnClickListener {
            (activity as HomeActivity).popbacktask()
        }

        //adapter

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rcv_job.layoutManager = linearLayoutManager
        jobAdapter = JobAdapter(activity!!, ArrayList<JobDto>())
        rcv_job.adapter = jobAdapter
    }

    override fun getJob(jobs: ArrayList<JobDto>) {

        for (i in 0 until jobs.size) {
            jobAdapter.setItem(jobs[i])

        }


    }
}
