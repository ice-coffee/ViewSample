package com.sample.view.recyclerview

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.common.utils.dp2px
import com.sample.view.R
import kotlinx.android.synthetic.main.activity_item_decoration.*

/**
 * @date: 2021/4/18
 * @author: ice_coffee
 * remark:
 */
class ItemDecorationActivity: AppCompatActivity() {

    var LayoutItemDecoration: LayoutItemDecoration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_decoration)
    }

    fun switchLinear(view: View) {
        recyclerView.layoutManager = LinearLayoutManager(this, if (rbVertical.isChecked) LinearLayoutManager.VERTICAL else LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ItemDecoAdapter(this)
        if (null != LayoutItemDecoration) {
            recyclerView.removeItemDecoration(LayoutItemDecoration!!)
        }
        LayoutItemDecoration = LayoutItemDecoration(dp2px(10), dp2px(5), rbInEdge.isChecked)
        recyclerView.addItemDecoration(LayoutItemDecoration!!)
    }

    fun switchGrid(view: View) {
        recyclerView.layoutManager = GridLayoutManager(this, 3, if (rbVertical.isChecked) GridLayoutManager.VERTICAL else GridLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ItemDecoAdapter(this)
        if (null != LayoutItemDecoration) {
            recyclerView.removeItemDecoration(LayoutItemDecoration!!)
        }
        LayoutItemDecoration = LayoutItemDecoration(dp2px(10), dp2px(5), rbInEdge.isChecked)
        recyclerView.addItemDecoration(LayoutItemDecoration!!)
    }

    class ItemDecoAdapter(private val context: Context, ) : RecyclerView.Adapter<ItemViewHolder>() {

        private val itemColorList = mutableListOf(
                "#CD950C", "#8B658B", "#FFC1C1", "#EEB4B4", "#CD9B9B",
                "#8B6969", "#FF6A6A", "#EE6363", "#CD5555", "#8B3A3A",
                "#FF8247", "#EE7942", "#CD6839", "#8B4726", "#FFD39B",
                "#8B658B", "#8B6969", "#EE6363", "#FFC1C1", "#EE6363",
                "#CD9B9B", "#FF6A6A", "#8B658B", "#8B6969", "#EE7942")

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_decoration, parent, false))
        }

        override fun getItemCount(): Int {
            return itemColorList.size
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.item.setBackgroundColor(Color.parseColor(itemColorList[position]))
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item = view.findViewById<View>(R.id.view)
    }
}