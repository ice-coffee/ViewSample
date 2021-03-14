package com.sample.view.recyclerview

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.sample.view.R
import kotlinx.android.synthetic.main.activity_recyclerview_layoutmanager.*

/**
 * @date: 2021/2/27
 * @author: ice_coffee
 * remark:
 */
class LayoutManagerActivity : AppCompatActivity() {

    private var mStudentList = mutableListOf<PersonBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview_layoutmanager)

        mStudentList.add(PersonBean("宁姚", PersonBean.GENDER_GRIL))
        mStudentList.add(PersonBean("陈平安", PersonBean.GENDER_BOY))
        mStudentList.add(PersonBean("舍月", PersonBean.GENDER_GRIL))
        mStudentList.add(PersonBean("刘羡阳", PersonBean.GENDER_BOY))
        mStudentList.add(PersonBean("顾燦", PersonBean.GENDER_BOY))
        mStudentList.add(PersonBean("暖树", PersonBean.GENDER_GRIL))
        mStudentList.add(PersonBean("景清", PersonBean.GENDER_BOY))
        mStudentList.add(PersonBean("周米粒", PersonBean.GENDER_GRIL))
        mStudentList.add(PersonBean("姜尚真", PersonBean.GENDER_BOY))

        val spannedGridLayoutManager = SpannedGridLayoutManager(SpannedGridLayoutManager.Orientation.VERTICAL, 3)
        spannedGridLayoutManager.itemOrderIsStable = true
        spannedGridLayoutManager.spanSizeLookup = SpannedGridLayoutManager.SpanSizeLookup { position: Int ->
            if (position % 4 == 0) SpanSize(2, 2) else SpanSize(1, 1)
        }

        val diffAdapter = StudentAdapter(this, mStudentList)
        recyclerView.layoutManager = spannedGridLayoutManager
        recyclerView.adapter = diffAdapter
        recyclerView.addItemDecoration(LayoutItemDecoration(10, false))
    }

    class StudentAdapter(context: Context, private val studentList: List<PersonBean>) : RecyclerView.Adapter<MyViewHolder>() {

        private val girlColor = "#FFD6E7"
        private val boyColor = "#BAE7FF"

        private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(layoutInflater.inflate(R.layout.item_student, parent, false))
        }

        override fun getItemCount(): Int {
            return studentList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            //4. 从新数据列表中获取最新数据
            val bean = studentList[position]
            when (bean.gender) {
                PersonBean.GENDER_GRIL -> {
                    holder.llRoot.setBackgroundColor(Color.parseColor(girlColor))
                    holder.ivIcon.setBackgroundResource(R.mipmap.girl)
                }
                PersonBean.GENDER_BOY -> {
                    holder.llRoot.setBackgroundColor(Color.parseColor(boyColor))
                    holder.ivIcon.setBackgroundResource(R.mipmap.boy)
                }
            }
            holder.tvName.text = bean.name
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val llRoot: LinearLayout = view.findViewById(R.id.ll_student_root)
        val ivIcon: ImageView = view.findViewById(R.id.iv_student_icon)
        val tvName: TextView = view.findViewById(R.id.tv_student_name)
    }

    class PersonBean(val name: String, val gender: Int) {
        companion object {
            const val GENDER_GRIL = 0
            const val GENDER_BOY = 1
        }
    }
}