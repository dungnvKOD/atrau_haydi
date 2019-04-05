package com.atrau.guider_haydi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.dto.Skill
import com.atrau.guider_haydi.R
import kotlinx.android.synthetic.main.item_skill_add.view.*
import android.view.MotionEvent
import android.widget.Toast
import android.view.View.OnTouchListener
import android.annotation.SuppressLint
import android.util.Log
import com.atrau.guider_haydi.view.skill.SkillFragment
import com.bumptech.glide.Glide


class MyAllSkillAdapter(val context: Context, var skills: ArrayList<Skill>) :
    RecyclerView.Adapter<MyAllSkillAdapter.MySkillViewHodel>() {
    companion object {
        val TAG = "MyAllSkillAdapter"
    }

    private val inflater = LayoutInflater.from(context)
    private lateinit var onMySkillAdapter: OnMySkillAdapter
    private var nameSkill: ArrayList<Skill> = ArrayList()
    private var check = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySkillViewHodel {
        val view = inflater.inflate(R.layout.item_skill_add, parent, false)
        return MySkillViewHodel(view)

    }

    override fun getItemCount(): Int {
        return skills.size

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MySkillViewHodel, position: Int) {
        if (holder is MySkillViewHodel) {
            val skill = skills[position]
            holder.txt_name_rating.text = skill.name

//            holder.rating.numStars = skill.level.toFloat().toInt()
//            holder.rating.rating = skill.level.toFloat()
            holder.rating.rating = skill.level.toFloat()
            holder.btn_delete.setOnClickListener { onMySkillAdapter.onMySkillAdapter(holder.adapterPosition) }
            val names: ArrayList<String> = ArrayList()

//            Log.d(TAG, skill.icon)
            Glide.with(context).load(skill.icon).into(holder.icon)

            if (check) {
                holder.icon.visibility = View.GONE
                holder.txt_name_rating.visibility = View.INVISIBLE
                holder.spinner.visibility = View.VISIBLE

                holder.rating.setOnTouchListener(OnTouchListener { v, event ->
                    if (event.action == MotionEvent.ACTION_UP) {
                        val touchPositionX = event.x
                        val width = holder.rating.width
                        val starsf = touchPositionX / width * 5.0f
                        val stars = starsf.toInt() + 1
                        holder.rating.rating = stars.toFloat()
                        skills[holder.adapterPosition].level = stars.toFloat().toDouble()
                        Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
                        v.isPressed = false
                    }
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        v.isPressed = true
                    }

                    if (event.action == MotionEvent.ACTION_CANCEL) {
                        v.isPressed = false
                    }
                    true
                })

                for (i in 0 until nameSkill.size) {
                    names.add(nameSkill[i].name!!)
                }

                var adapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, names);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
                holder.spinner.adapter = adapter

                holder.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        Toast.makeText(context, "$position-$id", Toast.LENGTH_SHORT).show()
//                        for (i in 0 until skills.size) {
//                            if (skills[i].name == nameSkill[position].name) {
//                               return
//                            }else if(skills[holder.adapterPosition].name)
//                        }
                        skills[holder.adapterPosition] = nameSkill[position]
                        Glide.with(context).load(skills[holder.adapterPosition].icon).into(holder.icon)

//                    nameSkill.removeAt(position)
//                    names.removeAt(position)
//                     adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, names);
//                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
//                    holder.spinner.adapter = adapter
                    }
                }

                check = false
            } else {
                holder.txt_name_rating.visibility = View.VISIBLE
                holder.spinner.visibility = View.INVISIBLE
            }


        } else if (holder is MySkillViewHodel) {

        }
    }

//    private fun spiner(holder: MySkillViewHodel,names:ArrayList<String>){
//
//    }


    fun setMySkillViewHodel(onMySkillAdapter: OnMySkillAdapter) {
        this.onMySkillAdapter = onMySkillAdapter
    }

    fun setSkill(skill: Skill, nameSkills: ArrayList<Skill>) {
        this.nameSkill.clear()
        this.nameSkill = nameSkills
        this.check = true
        skills.add(skill)
        notifyItemRangeInserted(skills.size, 1)

    }

    fun deleteSkill(possion: Int) {
        skills.removeAt(possion)
        notifyItemRemoved(possion)
    }

//    fun addItem(skill: Skill){
//
//
//
//    }

    fun getHereSkills(): ArrayList<Skill> {
        return this.skills
    }

    inner class MySkillViewHodel(view: View) : RecyclerView.ViewHolder(view) {
        val txt_name_rating: TextView = view.findViewById(R.id.txt_name_skill_add)
        val rating: RatingBar = view.ratingsBar
        val btn_delete: ImageButton = view.btn_delete
        val spinner: Spinner = view.sp_name_skill_add
        val icon: ImageView = view.icon_skill
    }

    interface OnMySkillAdapter {
        fun onMySkillAdapter(possion: Int)
    }
}