package com.atrau.guider_haydi.view.skill


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.atrau.guider_haydi.base.BaseDialog
import com.atrau.guider_haydi.dto.Skill
import kotlinx.android.synthetic.main.dialog_add_kill.*
import android.widget.RatingBar
import com.atrau.guider_haydi.R


class AddSkill(context: Context, var skills: ArrayList<Skill>, var dialogListener: DialogListener) : BaseDialog(context), View.OnClickListener {
    private lateinit var skill: Skill

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            super.onCreate(savedInstanceState)
        }
        setContentView(R.layout.dialog_add_kill)

        ratingsBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (fromUser) {
                ratingBar.rating = Math.ceil(rating.toDouble()).toFloat()
            }
        }

        btnCancelRate.setOnClickListener(this)
        btnSubmitRate.setOnClickListener(this)
        spinner()
        Toast.makeText(context, skills.size.toString(), Toast.LENGTH_LONG).show()


    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.btnCancelRate -> {
                cancel()

            }
            R.id.btnSubmitRate -> {
                skill.level = ratingsBar.rating.toDouble()
//                Toast.makeText(context, skill.name.toString() + "  and " + skill.level, Toast.LENGTH_LONG).show()
                dialogListener.onSkillListener(skill)

            }
        }
    }

    private fun spinner() {
        val arr: ArrayList<String> = ArrayList()
        for (i in 0 until skills.size) {
            arr.add(i, skills[i].name!!)

        }

        spinnerDL.setItems(arr)
        skill = skills[0]
        spinnerDL.selectedIndex = 0
        skill.level = ratingsBar.rating.toDouble()
        spinnerDL.setOnItemSelectedListener { view, position, id, item ->
            skill = skills[position]

        }
    }


    interface DialogListener {
        fun onSkillListener(skill: Skill)
    }

}