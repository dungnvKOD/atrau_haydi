package com.atrau.guider_haydi.view.profile


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.atrau.guider_haydi.adapter.MySkillAdapter
import com.atrau.guider_haydi.dto.GuideDto
import com.atrau.guider_haydi.dto.Skill
import com.atrau.guider_haydi.view.HomeActivity
import com.atrau.guider_haydi.conmon.Constant
import com.atrau.guider_haydi.dto.JobDto
import com.atrau.guider_haydi.view.skill.SkillFragment
import com.atrau.guider_haydi.App
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.view.job.JobFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@SuppressLint("ParcelCreator")
class ProfileFragment : Fragment(),
    OnProfileViewListenner, View.OnClickListener, HomeActivity.OnImageListener,
    SkillFragment.OnCkickSkillListener {


    private lateinit var frgSkill: SkillFragment
    private lateinit var profilePresenter: ProfilePresenter
    private lateinit var mySkillAdapter: MySkillAdapter
    private var checkPrice = false
    private var checkPhone = false
    private var checkDescription = false
    private var checkMyValue = false
    private var checkImage: String? = null
    private var checkJob: Boolean = false

    private var checkLanguages = false
    private var jobs: ArrayList<JobDto> = ArrayList()
    private var skills: ArrayList<Skill> = ArrayList()

//    private val GUIDER = "Guider"
//    private val SEMI_GUIDER = "Semi Guider"
//    private val CONDUTOR = "Condutor"
//    private val TRANSLATOR = "Translator"

    companion object {
        var TAG = "ProfileFragment"
        var newFragment = ProfileFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        skills.clear()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {


        frgSkill = SkillFragment.newFragment
        (activity as HomeActivity).setOnImageListener(this)
        profilePresenter = ProfilePresenter(this)
        profilePresenter.getFreeJob()
        profilePresenter.getProfile(App.getMyInsatnce().token, Constant.ALL)
        imgAvatarP.setOnClickListener(this)
        btn_add_avatarP.setOnClickListener(this)
        btn_add_cover.setOnClickListener(this)

        val linearLayoutManager = LinearLayoutManager(activity)
        rcv_skill.layoutManager = linearLayoutManager
        mySkillAdapter = MySkillAdapter(activity!!, skills)
        rcv_skill.adapter = mySkillAdapter

        btn_setting_priceP.setOnClickListener(this)
        btn_setting_emailP.setOnClickListener(this)
        btn_setting_repasswordP.setOnClickListener(this)
        btn_setting_txt_my_valueP.setOnClickListener(this)
        btn_setting_txt_descriptionP.setOnClickListener(this)
        btn_setting_txt_languagesP.setOnClickListener(this)
        btn_complete_emailP.setOnClickListener(this)
        btn_setting_txt_jobP.setOnClickListener(this)
        btn_setting_txt_skillP.setOnClickListener(this)
        profilePresenter.getSkill(App.getMyInsatnce().token)


        //TÉT
        fillJob()
    }

    // su kien click
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_add_cover -> {
                checkImage = Constant.COVER
                addAvatar()
            }
            R.id.btn_add_avatarP -> {
                checkImage = Constant.AVATAR
                addAvatar()
            }


            R.id.btn_setting_priceP -> {
                Log.d(TAG, "btn_setting_price")
                settingPrice()

            }
            R.id.btn_setting_emailP -> {
                Log.d(TAG, "btn_setting_emailP")
                settingEmail()

            }
            R.id.btn_setting_repasswordP -> {
                Log.d(TAG, "btn_setting_repasswordP")

            }

            R.id.btn_setting_txt_my_valueP -> {
                Log.d(TAG, "btn_setting_txt_my_valueP")
                settingValue()

            }
            R.id.btn_setting_txt_descriptionP -> {
                Log.d(TAG, "btn_setting_txt_descriptionP")
                settingDescription()
            }

            R.id.btn_setting_txt_languagesP -> {
                Log.d(TAG, "btn_setting_txt_languagesP")
                settingLanguages()
            }

            R.id.btn_setting_txt_jobP -> {
                (activity as HomeActivity).addOrShowFragment(JobFragment.newFragment, R.id.layout_kill, true, true)
//                settingJob()
            }

            R.id.btn_complete_emailP -> {
                progesbar(Constant.EMAIL)
                edt_setting_emailP.visibility = View.INVISIBLE
                btn_complete_emailP.visibility = View.GONE
                view_setting_emailP.visibility = View.VISIBLE
                val email = edt_setting_emailP.text.toString()

                val guideDto =
                    GuideDto(email, null, null, null, null, null, null, null, null, null, null)
                putData(guideDto, Constant.EMAIL)
                view_setting_emailP.text = ""
                checkPhone = false

            }

            R.id.btn_setting_txt_skillP -> {
                (activity as HomeActivity).addOrShowFragment(frgSkill, R.id.layout_kill, true, true)
                SkillFragment.newFragment.setOnCkickSkillListener(this)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun settingValue() {

        if (!checkMyValue) {
            //thi sua
            txt_write_my_valueP.visibility = View.GONE
            edt_write_my_valueP.visibility = View.VISIBLE
//            btn_setting_txt_my_valueP.text = getString(R.string.txt_save)
            btn_setting_txt_my_valueP.setTextColor(R.color.red)
            pb_my_value.visibility = View.GONE

            if (txt_write_my_valueP.text.trim() == getString(R.string.text_value).trim()) {
                edt_write_my_valueP.setText("")
            } else {
                edt_write_my_valueP.setText(txt_write_my_valueP.text.toString())

            }
            checkMyValue = true

        } else {
            //thi luu

            txt_write_my_valueP.visibility = View.VISIBLE
            edt_write_my_valueP.visibility = View.GONE
//            btn_setting_txt_my_valueP.text = getString(R.string.txt_sua)
            btn_setting_txt_my_valueP.setTextColor(R.color.red)
            txt_write_my_valueP.text = ""
            progesbar(Constant.MY_VALUE)

            val myValue = edt_write_my_valueP.text.toString()
            val guideDto = GuideDto(null, null, null, null, myValue, null, null, null, null, null, null)
            putData(guideDto, Constant.MY_VALUE)
            checkMyValue = false
            Log.d(TAG, "put my value...")
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun getMyValue(guideDto: GuideDto) {

        pb_my_value.visibility = View.GONE
        txt_write_my_valueP.text = guideDto.myValue
        Log.d(TAG, "getmyvalue ok ")
    }


    override fun getPhone(guideDto: GuideDto) {

    }

    private fun putData(guideDto: GuideDto, type: String) {
        profilePresenter.putProfile(guideDto, type)

    }

    private fun addAvatar() {
        checkPermisstion()
    }


    @SuppressLint("ResourceAsColor")
    private fun settingJob() {
        checkJob = !checkJob
    }

    override fun getJob(jobs: ArrayList<JobDto>) {



    }

    private fun fillJob() {
        val ids: ArrayList<Int> = ArrayList()
//        for (i in 0 until jobs.size) {
//            ids.add(jobs[i].id!!)
//        }

        ids.add(1)
        ids.add(2)
        ids.add(3)

        val hashMap: HashMap<String, ArrayList<Int>> = HashMap()
        hashMap.put("jobs", ids)
        profilePresenter.putJob(hashMap, Constant.JOB)


    }

    @SuppressLint("ResourceAsColor")
    private fun settingDescription() {
        if (!checkDescription) {
            //thi sua
            txt_write_descriptionP.visibility = View.INVISIBLE
            edt_write_descriptionP.visibility = View.VISIBLE
//            btn_setting_txt_descriptionP.text = getString(R.string.txt_save)
            btn_setting_priceP.setTextColor(R.color.red)
            if (txt_write_descriptionP.text.trim() == getString(R.string.text_description).trim()) {
                edt_write_descriptionP.setText("")
            } else {

                edt_write_descriptionP.setText(txt_write_descriptionP.text.toString())
            }
            checkDescription = true
        } else {
            //thi lu
            txt_jobP.visibility = View.GONE
            progesbar(Constant.DESCRRIPTION)
            txt_write_descriptionP.text = ""
            txt_write_descriptionP.visibility = View.VISIBLE
            edt_write_descriptionP.visibility = View.GONE
//            btn_setting_txt_descriptionP.text = getString(R.string.txt_sua)
            btn_setting_priceP.setTextColor(R.color.red)
            val description = edt_write_descriptionP.text.toString().trim()
            val guideDto =
                GuideDto(null, null, null, null, null, description, null, null, null, null, null)
            putData(guideDto, Constant.DESCRRIPTION)
            checkDescription = false

        }
    }

    @SuppressLint("ResourceAsColor")
    override fun getDescription(guideDto: GuideDto) {
        pb_description.visibility = View.INVISIBLE
        txt_write_descriptionP.text = guideDto.description

    }


    @SuppressLint("ResourceAsColor")
    private fun settingPrice() {
        //check neu chuoi ban dau khac chuoi sau khi luu thi gui len server

        if (!checkPrice) {
            //thi sua
            val str = txt_write_priceP.text.toString().trim()
            var price = ""
            var priceUntil = ""
            for (i in 0 until str.length) {
                if (str[i] in '0'..'9') {
                    price += str[i]

                } else {

                    priceUntil += str[i]
                }
            }

            edt_write_priceP.setText(price.trim())
            edt_write_price_unitP.setText(priceUntil.trim())
            txt_write_priceP.visibility = View.GONE
            edt_write_priceP.visibility = View.VISIBLE
            edt_write_price_unitP.visibility = View.VISIBLE
//            btn_setting_priceP.text = getString(R.string.txt_save)
            btn_setting_priceP.setTextColor(R.color.red)
            checkPrice = true
        } else {
            //thi luu
            progesbar(Constant.PRICE)
            txt_write_priceP.text = ""
            txt_write_priceP.visibility = View.VISIBLE
            edt_write_priceP.visibility = View.GONE
            edt_write_price_unitP.visibility = View.GONE
//            btn_setting_priceP.text = getString(R.string.txt_sua)
            btn_setting_priceP.setTextColor(R.color.red)
            val price = edt_write_priceP.text.toString().trim()
            val priceUnit = edt_write_price_unitP.text.toString()
            val guideDto =
                GuideDto(null, null, null, null, null, null, price.toInt(), priceUnit, null, null, null)
            putData(guideDto, Constant.PRICE)
            checkPrice = false
        }
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun getPrice(guideDto: GuideDto) {

        pb_price.visibility = View.GONE
        txt_write_priceP.text = guideDto.price.toString() + " " + guideDto.priceUnit

    }

    private fun settingEmail() {

        edt_setting_emailP.visibility = View.VISIBLE
        btn_complete_emailP.visibility = View.VISIBLE
        view_setting_emailP.visibility = View.INVISIBLE
        btn_setting_emailP.visibility = View.INVISIBLE
        edt_setting_emailP.setText(view_setting_emailP.text.toString())
        checkPhone = true
    }

    @SuppressLint("ResourceAsColor")
    private fun settingLanguages() {

        if (!checkLanguages) {
            //thi sua
            txt_write_languagesP.visibility = View.INVISIBLE
            layout_1_languagesP.visibility = View.VISIBLE
            checkLanguages = true
            //TODO...
            Log.d(TAG, "...checkLanguages ...false")

        } else {
            Log.d(TAG, "...checkLanguages ...true")
            txt_languagesP.visibility = View.VISIBLE
            //thi luu
//            txt_languagesP.visibility = View.VISIBLE
            progesbar(Constant.LANGUAGES)


//            var language: HashMap<String, ArrayList<String>> = HashMap()
            var arrLG: ArrayList<String> = ArrayList()
            if (cb_vietnamese.isChecked) {
                arrLG.add(" vietnamese")


            }
            if (cb_chinese.isChecked) {
                arrLG.add(" chinese")

            }
            if (cb_english.isChecked) {
                arrLG.add(" english")
            }
            if (cb_english.isChecked) {
                arrLG.add(" english")

            }
            if (cb_french.isChecked) {
                arrLG.add(" spain")
            }
            if (cb_spain.isChecked) {

                arrLG.add(" spain ")
            }

            Log.d(TAG, "dung...." + arrLG.toString())
//            var lg = arrLG.joinToString { "," }
            val lg = TextUtils.join(",", arrLG)

            val guideDto = GuideDto(null, null, null, null, null, null, null, null, null, null, lg.toString())
            putData(guideDto, Constant.LANGUAGES)

            txt_write_languagesP.visibility = View.VISIBLE
            btn_setting_txt_languagesP.setTextColor(R.color.red)
            layout_1_languagesP.visibility = View.GONE
            checkLanguages = false

        }
    }

    override fun getLangguages(guideDto: GuideDto) {
        Log.d(TAG, "dung...." + guideDto.languages)
        txt_write_languagesP.text = guideDto.languages
        pb_languages.visibility = View.INVISIBLE
    }

    override fun getEmail(guideDto: GuideDto) {
        btn_setting_emailP.visibility = View.VISIBLE
        pb_email.visibility = View.INVISIBLE
        view_setting_emailP.text = guideDto.email
        Toast.makeText(activity, "${guideDto.email}", Toast.LENGTH_LONG).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onIsPermisstionNotGranted() {
        //chua dc cap dc cap
        requestPermissions(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            Constant.REQESS_IMAGE
        )
    }

    private fun checkPermisstion() {
        if ((activity as HomeActivity).hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            openImage()
            Toast.makeText(activity, "da dc cap quyên", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(activity, "chua dc cap quyen", Toast.LENGTH_LONG).show()
            onIsPermisstionNotGranted()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constant.REQESS_IMAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //da dc cap
                openImage()
            }
        }
    }

    private fun openImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, Constant.REQESS_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val image = data.data
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap((activity as HomeActivity).contentResolver, image)

            if (checkImage == Constant.COVER) {
                val guideDto =
                    GuideDto(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        persistImage(bitmap).toString(),
                        null,
                        null
                    )
                putData(guideDto, Constant.COVER)
            } else {
                val guideDto =
                    GuideDto(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        persistImage(bitmap).toString(),
                        null
                    )
                profilePresenter.putProfile(guideDto, Constant.AVATAR)
            }
        }
    }

    override fun setImageListener(imageBitmap: Bitmap) {
        imgAvatarP.setImageBitmap(imageBitmap)
    }

    // callbacck
    @SuppressLint("SetTextI18n")
    override fun onGetProfileSuccess(guideDto: GuideDto) {

        txt_nameP.text = guideDto.name
        txt_cityP.text = guideDto.address
        view_setting_emailP.text = guideDto.email
        if (guideDto.quote == "null") {
            txt_coverP.text = ""

        } else {
            txt_coverP.text = guideDto.quote
        }

        txt_write_priceP.text = guideDto.price.toString() + " " + guideDto.priceUnit
        if (guideDto.jobs == "null" || guideDto.jobs == null || guideDto.jobs == "") {
//            txt_write_jobP.text = "Thêm công việc của bạn"

        } else {
//            txt_write_jobP.text = guideDto.jobs

        }

        imgAvatarP.setImageResource(R.drawable.ic_user)

        Glide.with(activity!!).load(guideDto.avatar).apply(
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
        )
            .into(object : SimpleTarget<Drawable>(200, 200) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
                ) {

                    imgAvatarP.setImageDrawable(resource)
                }
            })

        img_cover.setImageResource(R.drawable.ic_user)
        Glide.with(activity!!).load(guideDto.cover).apply(
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
        ).into(object : SimpleTarget<Drawable>(200, 200) {
            override fun onResourceReady(
                resource: Drawable,
                transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
            ) {

                img_cover.setImageDrawable(resource)
            }
        })

        if (!guideDto.myValue.equals("null")) {
            txt_write_my_valueP.text = guideDto.myValue

        } else {
            txt_write_my_valueP.text = getString(R.string.text_value)
        }

        if (!guideDto.description.equals("null")) {
            txt_write_descriptionP.text = guideDto.description
        } else {
            txt_write_descriptionP.text = getString(R.string.text_description)
        }

        if (!guideDto.languages.equals("null")) {
            txt_write_languagesP.text = guideDto.languages
        } else {
            txt_write_languagesP.text = getString(R.string.text_languages)
        }
    }

    private fun persistImage(bitmap: Bitmap): File? {
        val filesDir: File = (activity as HomeActivity).filesDir
        val currentTime: Date = Calendar.getInstance().time
        val name: String = "$currentTime"
        val imageFile: File = File(filesDir, "$name.jpg")
        val os: OutputStream
        return try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
            imageFile
        } catch (e: Exception) {
            Log.d(TAG, "dung...", e)
            null
        }
    }

    private fun progesbar(key: String) {
        if (key == Constant.KEY_PHONE) {

        } else if (key == Constant.EMAIL) {
            pb_email.visibility = View.VISIBLE
        } else if (key == Constant.MY_VALUE) {
            pb_my_value.visibility = View.VISIBLE
        } else if (Constant.DESCRRIPTION == key) {
            pb_description.visibility = View.VISIBLE
        } else if (key == Constant.LANGUAGES) {
            pb_languages.visibility = View.VISIBLE
        } else if (key == Constant.PRICE) {
            pb_price.visibility = View.VISIBLE
        } else if (key == Constant.JOB) {
            pb_job.visibility = View.VISIBLE
        }
    }

    override fun onGetProfileFaile() {
    }

    override fun getAvatar(guideDto: GuideDto) {
        Glide.with(activity!!).load(guideDto.avatar).into(imgAvatarP)
    }

    override fun getSkillSuccess(skill: ArrayList<Skill>) {
        (activity as HomeActivity).arrSkill = skill
        Log.d(TAG, "tất cả đều vào đây...")
        for (i in 0 until skill.size) {
            mySkillAdapter.setSkill(skill[i])
        }
    }

    override fun getSkillFaile() {
        Log.d(TAG, " getSkillFaile loi ...")
    }

    override fun getCover(guideDto: GuideDto) {
        Glide.with(activity!!).load(guideDto.cover).into(img_cover)
    }

    override fun onClickSkill(arrSkill: ArrayList<Skill>) {
        //TODO cua dung
        //khi add xong thi goi lại fun get skill lại
        profilePresenter.getSkill(App.getMyInsatnce().token)
        mySkillAdapter.clearItem()
    }

    override fun getMyJob(guideDto: GuideDto) {
        Log.d(TAG, guideDto.jobs + "dung")
//        txt_write_jobP.text = guideDto.jobs
        pb_job.visibility = View.INVISIBLE
    }

}
