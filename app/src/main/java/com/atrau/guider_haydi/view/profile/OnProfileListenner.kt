package com.atrau.guider_haydi.view.profile


import com.atrau.guider_haydi.dto.GuideDto
import com.atrau.guider_haydi.dto.JobDto
import com.atrau.guider_haydi.dto.Skill


interface OnProfileListenner {
    fun onGetProfileSuccess(guideDto: GuideDto)

    fun onGetProfileFaile()

    fun getAvatar(guideDto: GuideDto)
    fun getPrice(guideDto: GuideDto)
    fun getMyValue(guideDto: GuideDto)
    fun getDescription(guideDto: GuideDto)
    fun getLangguages(guideDto: GuideDto)
    fun getEmail(guideDto: GuideDto)
    fun getPhone(guideDto: GuideDto)
    fun getCover(guideDto: GuideDto)

    fun getSkillSuccess(skill: ArrayList<Skill>)
    fun getSkillFaile()
    fun getJob(jobs: ArrayList<JobDto>)

    fun getMyJob(guideDto: GuideDto)


}