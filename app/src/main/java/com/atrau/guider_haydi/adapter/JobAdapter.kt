package com.atrau.guider_haydi.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.dto.JobDto
import com.atrau.guider_haydi.dto.Skill
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_job.view.*

class JobAdapter(val context: Context, val jobs: ArrayList<JobDto>) :
    RecyclerView.Adapter<JobAdapter.MySkillViewHodel>() {
    companion object {
        var TAG = "JobAdapter"
    }

    private val inflater = LayoutInflater.from(context)
    private lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySkillViewHodel {
        val view = inflater.inflate(R.layout.item_job, parent, false)
        return MySkillViewHodel(view)
    }

    override fun getItemCount(): Int {
        return jobs.size
    }

    override fun onBindViewHolder(holder: MySkillViewHodel, position: Int) {
        val job = jobs[holder.adapterPosition]

        holder.itemView.setOnClickListener {
            job.isEmpty = !job.isEmpty
            Log.d(TAG, "onBindViewHolder...")

            if (job.isEmpty) {
                holder.itemView.setBackgroundResource(R.color.brown)
            } else {
                holder.itemView.setBackgroundResource(R.color.white)
            }
        }

        if (job.isEmpty) {
            holder.itemView.setBackgroundResource(R.color.brown)
        } else {

            holder.itemView.setBackgroundResource(R.color.white)
        }
        Glide.with(context).load(job.icon).into(holder.icon)
        holder.txt_name.text = job.name

    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener

    }

    fun setItem(jobDto: JobDto) {
        jobs.add(jobDto)
        notifyDataSetChanged()
    }


    inner class MySkillViewHodel(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.img_icon_job
        val txt_name: TextView = view.txt_name_job
    }

    interface OnClickListener {
        fun onClickItem()


    }
}