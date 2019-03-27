package com.atrau.guider_haydi.view.skill

import com.atrau.guider_haydi.dto.Skill


class SkillPresenter(var onViewSkillListener: OnViewSkillListener) :
    OnSkillModelListener {


    private val skillModel = SkillModel(this)

    fun getSkillAll() {
        skillModel.getSkillAll()

    }

    fun putSkills(skills: HashMap<String, ArrayList<HashMap<String, Any>>>) {

        skillModel.putSkills(skills)
    }

    override fun getSkillSuccess(skills: ArrayList<Skill>?) {
        onViewSkillListener.getSkillSuccess(skills!!)
    }

    override fun getSkillFaile() {
        onViewSkillListener.getSkillFaile()
    }

    override fun getMySkill(arrSkill: ArrayList<Skill>) {
        onViewSkillListener.getMySkill(arrSkill)
    }

}