package com.atrau.guider_haydi.view.job

import com.atrau.guider_haydi.dto.JobDto

interface OnJobModel {

    fun getJob(jobs: ArrayList<JobDto>)
}