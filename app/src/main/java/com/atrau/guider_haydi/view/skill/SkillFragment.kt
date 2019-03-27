package com.atrau.guider_haydi.view.skill

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.atrau.guider_haydi.adapter.MyAllSkillAdapter
import com.atrau.guider_haydi.dto.Skill
import com.atrau.guider_haydi.view.HomeActivity
import com.atrau.guider_haydi.R
import kotlinx.android.synthetic.main.fragment_skill.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.collections.ArrayList


class SkillFragment : Fragment(),
    OnViewSkillListener, View.OnClickListener, MyAllSkillAdapter.OnMySkillAdapter {


    private lateinit var dialog: AddSkill
    private val skillPresenter = SkillPresenter(this)
    private var skills: ArrayList<Skill> = ArrayList()
    private lateinit var myAllSkillAdapter: MyAllSkillAdapter
    private lateinit var onCkickSkillListener: OnCkickSkillListener
    private var check = ""

    companion object {
        val TAG = "SkillFragment"
        val newFragment = SkillFragment()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_skill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).setSupportActionBar(toolbar_add_skill)
        (activity as HomeActivity).supportActionBar!!.setDisplayShowTitleEnabled(true)
        toolbar_add_skill.navigationIcon = (activity as HomeActivity).resources.getDrawable(
            R.drawable.ic_arrow_back_black_24dp,
            (activity as HomeActivity).theme
        )

        toolbar_add_skill.setNavigationOnClickListener {
            skills.clear()
            (activity as HomeActivity).popbacktask()
        }
        (activity as HomeActivity).title = "KỸ NĂNG"

//        myAllSkillAdapter = MyAllSkillAdapter(activity!!, skills)
        init()
        skillPresenter.getSkillAll()
    }

    private fun init() {

        //TODO...
        val linearLayoutManager = LinearLayoutManager(activity)
        rcv_kill_add.layoutManager = linearLayoutManager
        myAllSkillAdapter = MyAllSkillAdapter(
            activity!!,
            (activity as HomeActivity).arrSkill!!
        )
        rcv_kill_add.adapter = myAllSkillAdapter
        btn_add_skill.setOnClickListener(this)
        btn_save_skill.setOnClickListener(this)
        myAllSkillAdapter.setMySkillViewHodel(this)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_add_skill -> {
                skillPresenter.getSkillAll()
                var signSkill: ArrayList<Skill> = ArrayList()
                Log.d(TAG, "my skill = " + myAllSkillAdapter.getHereSkills().size.toString())

                if (skills.size == myAllSkillAdapter.getHereSkills().size) {
//                    Toast.makeText(activity, "test: da co du skill ", Toast.LENGTH_LONG).show()
                } else {
                    // loc nhung thnag nao chua duoc them
                    signSkill.clear()

                    var arrTemp: ArrayList<Skill> = ArrayList()
                    if (myAllSkillAdapter.getHereSkills().size != 0) {

//                        checkSkill(skills)
                        Log.d(TAG, "recheck size= ")
                        arrTemp = skills
                        for (j in 0 until myAllSkillAdapter.getHereSkills().size) {
                            for (i in 0 until skills.size) {
                                if (skills[i].id == myAllSkillAdapter.getHereSkills()[j].id) {
                                    arrTemp.removeAt(i)
                                    break
                                }
                            }
                        }
                        if (signSkill.size != 0) {
                            signSkill.clear()
                        }
                        signSkill = arrTemp
                        //truwowsc khi bat dialog thi phai kiem tra xem no co khac 0 hay o
                        if (signSkill.size == 0) {
                            Toast.makeText(activity, "Đã đủ kỹ năng", Toast.LENGTH_LONG).show()
                            return
                        }
//                        Log.d(TAG, " ......................")
                    } else {
                        signSkill = skills
                    }

//                    Log.d(TAG, signSkill.size.toString() + "..." + myAllSkillAdapter.getHereSkills().size)

                    //lasy du lueu xong thi goi dialog

                    dialog = AddSkill(activity as HomeActivity,
                        skills,
                        @SuppressLint("ValidFragment")
                        object : AddSkill.DialogListener {
                            override fun onSkillListener(skill: Skill) {
                                // cho nay se set lai skill cho no
//                                Toast.makeText(context, skill.name, Toast.LENGTH_LONG).show()

                                dialog.cancel()
                                myAllSkillAdapter.setSkill(skill)
                            }
                        })
                    dialog.show()
                }
            }

            R.id.btn_save_skill -> {
                //TODO...
//                Toast.makeText(activity, "save...", Toast.LENGTH_LONG).show()

                val jsonArray = JSONArray()
                for (i in 0 until myAllSkillAdapter.getHereSkills().size) {
                    val skill = JSONObject()
                    try {
                        skill.put("skill_id", myAllSkillAdapter.getHereSkills()[i].id)
                        skill.put("name", myAllSkillAdapter.getHereSkills()[i].level)
                        jsonArray.put(skill)
                        check = "ADD"

                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                }

                val hasMap: HashMap<String, ArrayList<HashMap<String, Any>>> = HashMap()
                val arrHashMap: ArrayList<HashMap<String, Any>> = ArrayList()
//                var checkAddSkill = 0
                for (i in 0 until myAllSkillAdapter.getHereSkills().size) {
//                    checkAddSkill++
                    var objHashMap: HashMap<String, Any> = HashMap()
                    objHashMap.put("skill_id", myAllSkillAdapter.getHereSkills()[i].id.toString())
                    objHashMap.put("level", myAllSkillAdapter.getHereSkills()[i].level.toString())
                    arrHashMap.add(objHashMap)
                }
//                if (myAllSkillAdapter.getHereSkills().size!=0{
//
//
//                    }
                hasMap.put("skills", arrHashMap)
                skillPresenter.putSkills(hasMap)


            }
        }
    }

    override fun getMySkill(arrSkill: ArrayList<Skill>?) {
        Log.d(TAG, "size=" + arrSkill!!.size)
//        if (arrSkill.size != null||arrSkill.size !=0) {
        (activity as HomeActivity).arrSkill!!.clear()
        (activity as HomeActivity).arrSkill = arrSkill
//        }
        onCkickSkillListener.onClickSkill(skills)
        if (check == "ADD") {
            (activity as HomeActivity).popbacktask()
            check = ""
        }
    }

    override fun getSkillSuccess(skills: ArrayList<Skill>) {
        Log.d(TAG, " chay vao day may lan  size=... ${skills.size}")
//        this.skills.clear()
        this.skills = skills
        Log.d(TAG, "size update = ${this.skills.size}")
    }

    override fun onMySkillAdapter(possion: Int) {
        myAllSkillAdapter.deleteSkill(possion)
    }

    override fun getSkillFaile() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }
    // test o day

    override fun onPause() {
        super.onPause()
        skills.clear()
        Log.d(TAG, "onPause()")
    }

    fun setOnCkickSkillListener(onCkickSkillListener: OnCkickSkillListener) {
        this.onCkickSkillListener = onCkickSkillListener
    }

    interface OnCkickSkillListener {
        fun onClickSkill(arrSkill: ArrayList<Skill>)
    }


}