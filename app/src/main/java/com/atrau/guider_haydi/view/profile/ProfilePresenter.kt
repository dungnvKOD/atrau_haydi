package com.atrau.guider_haydi.view.profile

import android.util.Log
import com.atrau.guider_haydi.dto.GuideDto
import com.atrau.guider_haydi.dto.JobDto
import com.atrau.guider_haydi.dto.Skill


class ProfilePresenter(val onProfileViewListenner: OnProfileViewListenner) :
    OnProfileListenner {
    override fun getMyJob(guideDto: GuideDto) {
        onProfileViewListenner.getMyJob(guideDto)
    }


    private val profileModel = ProfileModel(this)

    fun getProfile(token: String, type: String) {
        profileModel.getProfile(token, type)
    }

    // "000@gmail.com", "0868852102", null, null, null, null, null, null, null, null, null
    fun putProfile(guideDto: GuideDto, type: String) {
        //TODO
        Log.d("1", "putProfile")
        profileModel.putProfile(guideDto, type)

    }

    fun getSkill(token: String) {
        profileModel.getSkill(token)
    }

    fun putJob(hashMap: HashMap<String, ArrayList<Int>>, jobType: String) {
        profileModel.putJob(hashMap, jobType)

    }

    fun getFreeJob() {
        profileModel.getJob()
    }

    override fun onGetProfileSuccess(guideDto: GuideDto) {
        onProfileViewListenner.onGetProfileSuccess(guideDto)
    }

    override fun onGetProfileFaile() {
        onProfileViewListenner.onGetProfileFaile()
    }


    override fun getAvatar(guideDto: GuideDto) {
        onProfileViewListenner.getAvatar(guideDto)
    }

    override fun getPrice(guideDto: GuideDto) {
        onProfileViewListenner.getPrice(guideDto)
    }

    override fun getMyValue(guideDto: GuideDto) {
        onProfileViewListenner.getMyValue(guideDto)
    }

    override fun getDescription(guideDto: GuideDto) {
        onProfileViewListenner.getDescription(guideDto)
    }

    override fun getLangguages(guideDto: GuideDto) {
        onProfileViewListenner.getLangguages(guideDto)
    }

    override fun getEmail(guideDto: GuideDto) {
        onProfileViewListenner.getEmail(guideDto)
    }

    override fun getPhone(guideDto: GuideDto) {
        onProfileViewListenner.getPhone(guideDto)
    }

    override fun getSkillSuccess(skill: ArrayList<Skill>) {
        onProfileViewListenner.getSkillSuccess(skill)
    }

    override fun getSkillFaile() {
        onProfileViewListenner.getSkillFaile()
    }

    override fun getCover(guideDto: GuideDto) {
        onProfileViewListenner.getCover(guideDto)
    }

    override fun getJob(jobs: ArrayList<JobDto>) {
        onProfileViewListenner.getJob(jobs)
    }
}