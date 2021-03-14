package com.sample.view.recyclerview

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.common.utils.dp2px
import com.sample.view.R
import kotlinx.android.synthetic.main.activity_item_animator.*

/**
 * @date: 2021/3/14
 * @author: ice_coffee
 * remark:
 */
class ItemAnimatorActivity : AppCompatActivity() {

    private lateinit var itemAnimatorAdapter: ItemAnimatorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_animator)

        itemAnimatorAdapter = ItemAnimatorAdapter(this, itemClickListener)
        val layoutManager = CustomLinearLayoutManager(this)
        rvItemAnimator.layoutManager = layoutManager
        rvItemAnimator.adapter = itemAnimatorAdapter

        cbPre.setOnCheckedChangeListener { _, isChecked ->
            layoutManager.setSupport(isChecked)
        }
        cbAnimaNull.setOnCheckedChangeListener { _, isChecked ->
            rvItemAnimator.itemAnimator = if (isChecked) DefaultItemAnimator() else null
        }
    }

    private class CustomLinearLayoutManager(context: Context?) : LinearLayoutManager(context) {

        private var isSupport = true

        override fun supportsPredictiveItemAnimations(): Boolean {
            return isSupport
        }

        fun setSupport(isSupport: Boolean) {
            this.isSupport = isSupport
        }
    }

    private val itemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            if (rdDelete.isChecked) {
                itemAnimatorAdapter.deleteItem(position)
            }
            if (rdAdd.isChecked) {
                itemAnimatorAdapter.addItem(position)
            }
            if (rdChange.isChecked) {
                itemAnimatorAdapter.changeItem(position)
            }
        }
    }

    class ItemAnimatorAdapter(private val context: Context, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<ItemAnimatorViewHolder>() {

        private val itemColorList = mutableListOf(ItemBean("#CD950C", 100),
                ItemBean("#8B658B", 100),
                ItemBean("#FFC1C1", 100),
                ItemBean("#EEB4B4", 100),
                ItemBean("#CD9B9B", 100),
                ItemBean("#8B6969", 100),
                ItemBean("#FF6A6A", 100),
                ItemBean("#EE6363", 100),
                ItemBean("#CD5555", 100),
                ItemBean("#8B3A3A", 100),
                ItemBean("#FF8247", 100),
                ItemBean("#EE7942", 100),
                ItemBean("#CD6839", 100),
                ItemBean("#8B4726", 100),
                ItemBean("#FFD39B", 100))

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAnimatorViewHolder {
            return ItemAnimatorViewHolder(LayoutInflater.from(context).inflate(R.layout.item_animator, parent, false))
        }

        override fun getItemCount(): Int {
            return itemColorList.size
        }

        override fun onBindViewHolder(holder: ItemAnimatorViewHolder, position: Int) {
            val itemBean = itemColorList[position]
            holder.itemAnim.text = itemBean.color
            holder.itemAnim.setBackgroundColor(Color.parseColor(itemBean.color))
            val params = holder.itemAnim.layoutParams
            params.height = dp2px(itemBean.height)
            holder.itemAnim.layoutParams = params
            holder.itemAnim.setOnClickListener { view ->
                run {
                    itemClickListener.onItemClick(view, holder.adapterPosition)
                }
            }
        }

        fun deleteItem(position: Int) {
            itemColorList.removeAt(position)
            notifyItemRemoved(position)
        }

        fun addItem(position: Int) {
            itemColorList.add(position, ItemBean("#e91e63", 100))
            notifyItemInserted(position)
        }

        fun changeItem(position: Int) {
            itemColorList[position].height = 20
            notifyItemChanged(position)
        }
    }

    class ItemAnimatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemAnim: TextView = itemView.findViewById(R.id.tvItemName)
    }

    data class ItemBean(val color: String, var height: Int)

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}