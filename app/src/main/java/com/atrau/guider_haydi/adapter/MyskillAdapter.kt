package com.atrau.guider_haydi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.dto.Skill
import com.atrau.guider_haydi.R
import kotlinx.android.synthetic.main.item_my_skill.view.*

class MySkillAdapter(val context: Context, var skills: MutableList<Skill>) :
    RecyclerView.Adapter<MySkillAdapter.MySkillViewHodel>() {
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySkillViewHodel {
        val view = inflater.inflate(R.layout.item_my_skill, parent, false)
        return MySkillViewHodel(view)
    }

    override fun getItemCount(): Int {
        return skills.size
    }

    override fun onBindViewHolder(holder: MySkillViewHodel, position: Int) {
        if (holder is MySkillViewHodel) {
            val skill = skills[position]
            holder.txt_name_rating.text = skill.name
            holder.rating.rating = skill.level.toFloat()
        } else if (holder is MySkillViewHodel) {
        }
    }

    fun setSkill(skill: Skill) {
        skills.add(skill)
        notifyDataSetChanged()
    }

    fun clearItem() {
        this.skills.clear()
        notifyDataSetChanged()

    }


    inner class MySkillViewHodel(view: View) : RecyclerView.ViewHolder(view) {
        val txt_name_rating: TextView = view.txt_name_skill
        val rating: RatingBar = view.rating_bar
    }
}