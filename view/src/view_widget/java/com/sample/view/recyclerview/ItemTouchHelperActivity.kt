package com.sample.view.recyclerview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.view.R
import kotlinx.android.synthetic.main.activity_recyclerview_itemtouchhelper.*
import java.util.*


/**
 * @date: 2021/2/27
 * @author: ice_coffee
 * remark:
 */
class ItemTouchHelperActivity: AppCompatActivity() {

    private lateinit var itemtouchAdapter: ItemTouchAdapter
    private val itemList = arrayListOf("星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview_itemtouchhelper)

        itemtouchAdapter = ItemTouchAdapter(this, itemList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemtouchAdapter
        recyclerView.addItemDecoration(LayoutItemDecoration(8, true))

        val touchCallBack = MyItemTouchCallBack(onItemTouchListener)
        val itemTouchHelper = ItemTouchHelper(touchCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private val onItemTouchListener = object : MyItemTouchCallBack.OnItemTouchListener {
        override fun onMove(fromPosition: Int, toPosition: Int) {
            itemtouchAdapter.notifyItemMoved(fromPosition, toPosition)
            Collections.swap(itemList, fromPosition, toPosition)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                runViewAnimation(viewHolder!!.itemView, true)
            }
        }

        override fun onSwiped(position: Int) {
            itemtouchAdapter.notifyItemRemoved(position)
            itemList.removeAt(position)
        }

        override fun onClear(viewHolder: RecyclerView.ViewHolder) {
            runViewAnimation(viewHolder.itemView, false)
        }
    }

    /**
     * 动画
     */
    private fun runViewAnimation(view: View?, isSelected: Boolean) {
        if (null != view) {
            val xScaleAnimator = ObjectAnimator.ofFloat(view, "scaleX", if (isSelected) 1.04f else 1.0f)
            val yScaleAnimator = ObjectAnimator.ofFloat(view, "scaleY", if (isSelected) 1.04f else 1.0f)
            val animatorSet = AnimatorSet()
            animatorSet.duration = 200
            animatorSet.play(xScaleAnimator).with(yScaleAnimator)
            animatorSet.start()
        }
    }
}

class ItemTouchAdapter(val context: Context, private val itemList: List<String>) : RecyclerView.Adapter<ItemTouchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTouchViewHolder {
        return ItemTouchViewHolder(LayoutInflater.from(context).inflate(R.layout.view_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemTouchViewHolder, position: Int) {
        holder.tvItem.text = itemList[position]
    }
}

class ItemTouchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvItem: TextView = view.findViewById(R.id.tvItem)
}