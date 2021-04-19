package com.sample.view.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.util.*

/**
 * date: 2020/11/28
 * author: ice_coffee
 * remark: 通用 ItemDecoration 工具
 */
class LayoutItemDecoration : RecyclerView.ItemDecoration {

    /**
     * 水平方向间隔
     */
    var horizontalSpacing: Int

    /**
     * 垂直方向间隔
     */
    var verticalSpacing: Int

    /**
     * 是否设置边缘间距
     */
    var includeEdge: Boolean

    constructor(spacing: Int, includeEdge: Boolean) {
        this.horizontalSpacing = spacing
        this.verticalSpacing = spacing
        this.includeEdge = includeEdge
    }

    constructor(horizontalSpacing: Int, verticalSpacing: Int, includeEdge: Boolean) {
        this.horizontalSpacing = horizontalSpacing
        this.verticalSpacing = verticalSpacing
        this.includeEdge = includeEdge
    }

    /**
     * position 和对应 view 所在列的对应关系
     */
    private var positionToColumn: MutableMap<Int, Int>? = null
    /**
     * 下标对应列, 值对应某列当前最远的距离
     */
    private var endLineToColum: IntArray? = null

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        //item view 总数
        val itemCount = parent.adapter?.itemCount ?: 0
        //当前 item view 的 adapter 位置
        val position = parent.getChildAdapterPosition(view)
        when (val layoutManager = parent.layoutManager) {
            is GridLayoutManager -> {
                setGridItemDecoration(layoutManager, outRect, layoutManager.spanCount, position, itemCount)
            }
            is StaggeredGridLayoutManager -> {
                setStaggeredGridItemDecoration(layoutManager, outRect, layoutManager.spanCount, position, itemCount)
            }
            is LinearLayoutManager -> {
                setLinearItemDecoration(outRect, layoutManager.orientation, position, itemCount)
            }
            else -> {
                outRect.set(horizontalSpacing, verticalSpacing, horizontalSpacing, verticalSpacing)
            }
        }
    }

    /**
     * 设置线性布局分割线
     */
    private fun setLinearItemDecoration(outRect: Rect, orientation: Int, position: Int, itemCount: Int) {
        val horizontalRect = if (includeEdge) horizontalSpacing else 0
        val verticalRect = if (includeEdge) verticalSpacing else 0
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            when (position) {
                0 -> outRect.set(horizontalRect, verticalRect, horizontalSpacing, verticalRect)
                itemCount - 1 -> outRect.set(0, verticalRect, horizontalRect, verticalRect)
                else -> outRect.set(0, verticalRect, horizontalSpacing, verticalRect)
            }
        } else {
            when (position) {
                0 -> outRect.set(horizontalRect, verticalRect, horizontalRect, verticalSpacing)
                itemCount - 1 -> outRect.set(horizontalRect, 0, horizontalRect, verticalRect)
                else -> outRect.set(horizontalRect, 0, horizontalRect, verticalSpacing)
            }
        }
    }

    /**
     * 设置网格布局分割线
     */
    private fun setGridItemDecoration(layoutManager: GridLayoutManager, outRect: Rect, spanCount: Int, position: Int, itemCount: Int) {
        //当前列
        val column = position % spanCount
        calculateGridOutRectByColum(outRect, spanCount, itemCount, position, layoutManager.orientation, column)
    }

    /**
     * 设置瀑布流布局分割线
     */
    private fun setStaggeredGridItemDecoration(layoutManager: StaggeredGridLayoutManager, outRect: Rect, spanCount: Int, position: Int, itemCount: Int) {
        if (null == positionToColumn) {
            positionToColumn = HashMap()
        }
        if (null == endLineToColum) {
            endLineToColum = IntArray(spanCount)
        }

        //当前 view 应处的列
        var column = 0
        if (positionToColumn!!.containsKey(position)) {
            column = positionToColumn!![position]!!
        } else {
            if (position > 0) {
                val lastViewPosition = position - 1
                val lastView = layoutManager.findViewByPosition(lastViewPosition)
                if (null != lastView) {
                    val lastViewColumn = positionToColumn!![lastViewPosition]!!
                    if (layoutManager.orientation == StaggeredGridLayoutManager.VERTICAL) {
                        endLineToColum!![lastViewColumn] = endLineToColum!![lastViewColumn] + lastView.height
                    } else {
                        endLineToColum!![lastViewColumn] = endLineToColum!![lastViewColumn] + lastView.width
                    }
                }
            }
            //首行判断
            if (position < spanCount) {
                column = position
            } else {
                var minLine = Int.MAX_VALUE
                for (i in spanCount - 1 downTo 0) {
                    if (endLineToColum!![i] <= minLine) {
                        minLine = endLineToColum!![i]
                        column = i
                    }
                }
            }
            positionToColumn!![position] = column
        }

        calculateGridOutRectByColum(outRect, spanCount, itemCount, position, layoutManager.orientation, column)
    }

    /**
     * 计算间隔
     */
    private fun calculateGridOutRectByColum(outRect: Rect, spanCount: Int, itemCount: Int, position: Int, orientation: Int, column: Int) {
        //是否需要包含边界
        if (includeEdge) {
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                outRect.left = horizontalSpacing - column * horizontalSpacing / spanCount
                outRect.right = (column + 1) * horizontalSpacing / spanCount
                //第一行判断
                if (position < spanCount) {
                    outRect.top = verticalSpacing
                }
                outRect.bottom = verticalSpacing
            } else {
                outRect.top = verticalSpacing - column * verticalSpacing / spanCount
                outRect.bottom = (column + 1) * verticalSpacing / spanCount
                //第一行判断
                if (position < spanCount) {
                    outRect.left = horizontalSpacing
                }
                outRect.right = horizontalSpacing
            }
        } else {
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                outRect.left = column * horizontalSpacing / spanCount
                outRect.right = horizontalSpacing - (column + 1) * horizontalSpacing / spanCount
                outRect.top = 0
                //最后一行判断
                if (position + spanCount < itemCount) {
                    outRect.bottom = verticalSpacing
                }
            } else {
                outRect.top = column * verticalSpacing / spanCount
                outRect.bottom = verticalSpacing - (column + 1) * verticalSpacing / spanCount
                outRect.left = 0
                //最后一行判断
                if (position + spanCount < itemCount) {
                    outRect.right = horizontalSpacing
                }
            }
        }
    }
}