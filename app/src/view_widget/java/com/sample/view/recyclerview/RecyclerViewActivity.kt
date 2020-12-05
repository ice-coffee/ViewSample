package com.sample.view.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.Callback
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.view.R
import kotlinx.android.synthetic.main.activity_recycler_view.*

/**
 * date: 2020/11/26
 * author: ice_coffee
 * remark:
 */
public class RecyclerViewActivity : AppCompatActivity() {

    private var oldStudentList = mutableListOf<StudentBean>()
    private var newStudentList = mutableListOf<StudentBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        oldStudentList.add(StudentBean(0, "陈平安", 23, StudentBean.GENDER_BOY))
        oldStudentList.add(StudentBean(1, "宁姚", 23, StudentBean.GENDER_GRIL))
        oldStudentList.add(StudentBean(2, "刘羡阳", 23, StudentBean.GENDER_BOY))
        oldStudentList.add(StudentBean(3, "舍月", 23, StudentBean.GENDER_GRIL))

        newStudentList.add(StudentBean(0, "陈平安", 43, StudentBean.GENDER_BOY))
        newStudentList.add(StudentBean(4, "顾燦", 43, StudentBean.GENDER_BOY))
        newStudentList.add(StudentBean(2, "刘羡阳", 43, StudentBean.GENDER_BOY))

        val diffAdapter = StudentAdapter(this, oldStudentList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = diffAdapter
        recyclerView.addItemDecoration(LayoutItemDecoration(10, false))

        btDiff.setOnClickListener {
            val diffResult = DiffUtil.calculateDiff(object : Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldStudentList[oldItemPosition].id == newStudentList[newItemPosition].id
                }

                override fun getOldListSize(): Int {
                    return oldStudentList.size
                }

                override fun getNewListSize(): Int {
                    return newStudentList.size
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldStudentList[oldItemPosition].name == newStudentList[newItemPosition].name
                }
            })

            diffResult.dispatchUpdatesTo(diffAdapter)
            oldStudentList.clear()
            oldStudentList.addAll(newStudentList)
        }
    }
}