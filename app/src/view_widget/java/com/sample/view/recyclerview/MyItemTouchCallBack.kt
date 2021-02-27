package com.sample.view.recyclerview

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * @date: 2021/2/27
 * @author: ice_coffee
 * remark:
 */
class MyItemTouchCallBack(private val onItemTouchListener: OnItemTouchListener) : ItemTouchHelper.Callback() {

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return if (recyclerView.layoutManager is LinearLayoutManager) {
            makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT)
        } else {
            makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, 0)
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //此处是侧滑删除的主要代码
        val position = viewHolder.adapterPosition
        onItemTouchListener.onSwiped(position)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition
        onItemTouchListener.onMove(fromPosition, toPosition)
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        onItemTouchListener.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        onItemTouchListener.onClear(viewHolder)
    }

    /**
     * 移动交换数据的更新监听
     */
    interface OnItemTouchListener {
        /**
         * 拖拽滑动Item时调用
         */
        fun onMove(fromPosition: Int, toPosition: Int)

        /**
         * 侧滑Item时调用
         */
        fun onSwiped(position: Int)

        /**
         * 状态改变时调用
         */
        fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int)

        /**
         * ViewHolder 释放时使用
         */
        fun onClear(viewHolder: RecyclerView.ViewHolder)
    }
}