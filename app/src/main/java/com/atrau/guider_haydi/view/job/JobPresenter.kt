package com.atrau.guider_haydi.view.job

import com.atrau.guider_haydi.dto.JobDto

class JobPresenter(val jobViewLestener: JobViewLestener) : OnJobModel {


    private val jobModel = JobModel(this)

    fun getJob() {
        jobModel.getJob()
    }


    override fun getJob(jobs: ArrayList<JobDto>) {

        jobViewLestener.getJob(jobs)
    }
}