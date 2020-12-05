package com.sample.view.recyclerview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sample.view.R

/**
 *  @author mzp
 *  date : 2020/11/13
 *  desc :
 */
class StudentAdapter(context: Context, val studentList: List<StudentBean>) : RecyclerView.Adapter<StudentAdapter.MyViewHolder>() {

    private val girlColor = "#FFD6E7"
    private val boyColor = "#BAE7FF"

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.MyViewHolder {
        return MyViewHolder(layoutInflater.inflate(R.layout.item_student, parent, false))
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentAdapter.MyViewHolder, position: Int) {
        val studentBean = studentList[position]
        when (studentBean.gender) {
            StudentBean.GENDER_GRIL -> {
                holder.rlRoot.setBackgroundColor(Color.parseColor(girlColor))
                holder.ivIcon.setBackgroundResource(R.mipmap.girl)
            }
            StudentBean.GENDER_BOY -> {
                holder.rlRoot.setBackgroundColor(Color.parseColor(boyColor))
                holder.ivIcon.setBackgroundResource(R.mipmap.boy)
            }
        }
        holder.tvName.text = studentBean.name
        holder.tvAge.text = studentBean.age.toString()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val rlRoot: RelativeLayout = view.findViewById(R.id.rl_student_root)
        val ivIcon: ImageView = view.findViewById(R.id.iv_student_icon)
        val tvName: TextView = view.findViewById(R.id.tv_student_name)
        val tvAge: TextView = view.findViewById(R.id.tv_student_age)
    }
}