package com.sample.view.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sample.common.utils.LogUtils

/**
 * date: 2020/11/28
 * author: ice_coffee
 * remark:
 */
class LayoutItemDecoration(private val spacing: Int, private val includeEdge: Boolean) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        //item view 总数
        val itemCount = parent.adapter?.itemCount ?: 0
        //当前 item view 的 adapter 位置
        val position = parent.getChildAdapterPosition(view)
        when (val layoutManager = parent.layoutManager) {
            is GridLayoutManager -> {
                setGridItemDecoration(outRect, layoutManager.spanCount, position, itemCount)
            }
            is StaggeredGridLayoutManager -> {
                setGridItemDecoration(outRect, layoutManager.spanCount, position, itemCount)
            }
            is LinearLayoutManager -> {
                setLinearItemDecoration(outRect, layoutManager.orientation, position, itemCount)
            }
            else -> {
                outRect.set(spacing, spacing, spacing, spacing)
            }
        }
    }

    /**
     * 设置线性布局分割线
     */
    private fun setLinearItemDecoration(outRect: Rect, orientation: Int, position: Int, itemCount: Int) {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            when (position) {
                0 -> outRect.set(spacing, 0, spacing, 0)
                itemCount - 1 -> outRect.set(0, 0, if (includeEdge) spacing else 0, 0)
                else -> outRect.set(0, 0, spacing, 0)
            }
        } else {
            when (position) {
                0 -> outRect.set(0, spacing, 0, spacing)
                itemCount - 1 -> outRect.set(0, 0, 0, if (includeEdge) spacing else 0)
                else -> outRect.set(0, 0, 0, spacing)
            }
        }
    }

    /**
     * 设置瀑布流分割线
     */
    private fun setGridItemDecoration(outRect: Rect, spanCount: Int, position: Int, itemCount: Int) {
        LogUtils.e("gridItemd")
        //当前列
        val column = position % spanCount
        //是否需要包含边界
        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount
            //第一行判断
            if (position < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            outRect.top = 0
            //最后一行判断
            if (position + spanCount < itemCount) {
                outRect.bottom = spacing
            }
        }
    }
}