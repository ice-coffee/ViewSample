package com.sample.view.recyclerview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.view.R

/**
 *  @author mzp
 *  date : 2020/11/13
 *  desc :
 */
class StudentAdapter(context: Context) : RecyclerView.Adapter<StudentAdapter.MyViewHolder>() {

    private val girlColor = "#FFD6E7"
    private val boyColor = "#BAE7FF"

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    //1. 声明 DiffUtil.ItemCallback 回调
    private val itemCallback = object : DiffUtil.ItemCallback<StudentBean>() {
        override fun areItemsTheSame(oldItem: StudentBean, newItem: StudentBean): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StudentBean, newItem: StudentBean): Boolean {
            return oldItem.name == newItem.name && oldItem.age == newItem.age
        }
    }

    //2. 创建 AsyncListDiff 对象
    private val mDiffer = AsyncListDiffer<StudentBean>(this, itemCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.MyViewHolder {
        return MyViewHolder(layoutInflater.inflate(R.layout.item_student, parent, false))
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    fun submitList(studentList: List<StudentBean>) {
        //3. 提交新数据列表
        mDiffer.submitList(studentList)
    }

    override fun onBindViewHolder(holder: StudentAdapter.MyViewHolder, position: Int) {
        //4. 从新数据列表中获取最新数据
        val studentBean = mDiffer.currentList[position]
        when (studentBean.gender) {
            StudentBean.GENDER_GRIL -> {
                holder.llRoot.setBackgroundColor(Color.parseColor(girlColor))
                holder.ivIcon.setBackgroundResource(R.mipmap.girl)
            }
            StudentBean.GENDER_BOY -> {
                holder.llRoot.setBackgroundColor(Color.parseColor(boyColor))
                holder.ivIcon.setBackgroundResource(R.mipmap.boy)
            }
        }
        holder.tvName.text = studentBean.name
        holder.tvAge.text = studentBean.age.toString()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val llRoot: LinearLayout = view.findViewById(R.id.ll_student_root)
        val ivIcon: ImageView = view.findViewById(R.id.iv_student_icon)
        val tvName: TextView = view.findViewById(R.id.tv_student_name)
        val tvAge: TextView = view.findViewById(R.id.tv_student_age)
    }
}