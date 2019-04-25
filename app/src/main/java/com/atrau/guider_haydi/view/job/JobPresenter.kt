package com.atrau.guider_haydi.view.job

import com.atrau.guider_haydi.dto.JobDto

class JobPresenter(val jobViewLestener: JobViewLestener) : OnJobModel {


    private val jobModel = JobModel(this)

    fun putJob(hashMap: HashMap<String, ArrayList<Int>>, jobType: String) {
        jobModel.putJob(hashMap, jobType)

    }

    override fun putJob() {
        jobViewLestener.putJob()

    }
}