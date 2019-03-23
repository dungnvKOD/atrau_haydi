package com.atrau.guider_haydi.view.skill


import com.atrau.guider_haydi.dto.Skill

interface OnSkillModelListener {
    fun getSkillSuccess(skills: ArrayList<Skill>)
    fun getSkillFaile()
    fun getMySkill(arrSkill:ArrayList<Skill>)
}