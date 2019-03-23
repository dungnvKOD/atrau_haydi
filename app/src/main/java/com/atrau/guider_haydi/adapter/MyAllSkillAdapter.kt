package com.atrau.guider_haydi.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.dto.Skill
import com.atrau.guider_haydi.R
import kotlinx.android.synthetic.main.item_skill_add.view.*


class MyAllSkillAdapter(val context: Context, var skills: ArrayList<Skill>) : RecyclerView.Adapter<MyAllSkillAdapter.MySkillViewHodel>() {

    private val inflater = LayoutInflater.from(context)
    private lateinit var onMySkillAdapter: OnMySkillAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySkillViewHodel {
        val view = inflater.inflate(R.layout.item_skill_add, parent, false)
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
            holder.btn_delete.setOnClickListener { onMySkillAdapter.onMySkillAdapter(holder.adapterPosition) }

        } else if (holder is MySkillViewHodel) {

        }
    }

    fun setMySkillViewHodel(onMySkillAdapter: OnMySkillAdapter) {
        this.onMySkillAdapter = onMySkillAdapter

    }

    fun setSkill(skill: Skill) {
        skills.add(skill)
        notifyDataSetChanged()
    }

    fun deleteSkill(possion: Int) {
        skills.removeAt(possion)
        notifyItemRemoved(possion)
    }

    fun getHereSkills(): ArrayList<Skill> {
        return this.skills
    }

    inner class MySkillViewHodel(view: View) : RecyclerView.ViewHolder(view) {
        val txt_name_rating: TextView = view.findViewById(R.id.txt_name_skill_add)
        val rating: RatingBar = view.ratingsBar
        val btn_delete: ImageButton = view.btn_delete
    }

    interface OnMySkillAdapter {
        fun onMySkillAdapter(possion: Int)
    }
}