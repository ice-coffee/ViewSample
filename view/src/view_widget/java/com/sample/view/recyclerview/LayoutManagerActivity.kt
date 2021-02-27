package com.sample.view.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
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

    private var oldStudentList = mutableListOf<StudentBean>()
    private var newStudentList = mutableListOf<StudentBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview_layoutmanager)

        newStudentList.add(StudentBean(0, "宁姚", 23, StudentBean.GENDER_GRIL))
        newStudentList.add(StudentBean(1, "陈平安", 23, StudentBean.GENDER_BOY))
        newStudentList.add(StudentBean(2, "舍月", 23, StudentBean.GENDER_GRIL))
        newStudentList.add(StudentBean(3, "刘羡阳", 23, StudentBean.GENDER_BOY))
        newStudentList.add(StudentBean(4, "顾燦", 23, StudentBean.GENDER_BOY))

        oldStudentList.add(StudentBean(1, "陈平安", 23, StudentBean.GENDER_BOY))
        oldStudentList.add(StudentBean(3, "刘羡阳", 23, StudentBean.GENDER_BOY))
        oldStudentList.add(StudentBean(4, "顾燦", 23, StudentBean.GENDER_BOY))

        val spannedGridLayoutManager = SpannedGridLayoutManager(SpannedGridLayoutManager.Orientation.VERTICAL, 3)
        spannedGridLayoutManager.itemOrderIsStable = true
        spannedGridLayoutManager.spanSizeLookup = SpannedGridLayoutManager.SpanSizeLookup { position: Int ->
            if (position == 0) SpanSize(2, 2) else SpanSize(1, 1)
        }

        val diffAdapter = StudentAdapter(this)
        recyclerView.layoutManager = spannedGridLayoutManager
        recyclerView.adapter = diffAdapter
        recyclerView.addItemDecoration(LayoutItemDecoration(10, false))

        /**
         * 既然是动画，就会有时间，我们把动画执行时间变大一点来看一看效果
         */
        /**
         * 既然是动画，就会有时间，我们把动画执行时间变大一点来看一看效果
         */
        val defaultItemAnimator = DefaultItemAnimator()
        defaultItemAnimator.addDuration = 200
        defaultItemAnimator.removeDuration = 200
        recyclerView.itemAnimator = defaultItemAnimator

        diffAdapter.submitList(oldStudentList)

        btDiff.setOnClickListener { diffAdapter.submitList(newStudentList) }

        add.setOnClickListener { diffAdapter.notifyItemInserted(3) }
        remove.setOnClickListener { diffAdapter.notifyItemRemoved(3) }
        change.setOnClickListener { diffAdapter.notifyItemChanged(3) }

    }
}