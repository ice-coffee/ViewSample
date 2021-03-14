package com.sample.view.recyclerview

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.view.R
import kotlinx.android.synthetic.main.activity_diffutil.*
import kotlinx.android.synthetic.main.activity_recyclerview_layoutmanager.recyclerView

/**
 * @date: 2021/3/14
 * @author: ice_coffee
 * remark:
 */
class DiffUtilActivity : AppCompatActivity() {

    private val itemColorList = mutableListOf(
            ColorBean("#CD950C", 0),
            ColorBean("#8B658B", 1),
            ColorBean("#FFC1C1", 2),
            ColorBean("#EEB4B4", 3),
            ColorBean("#CD9B9B", 4),
            ColorBean("#8B6969", 5),
            ColorBean("#FF6A6A", 6),
            ColorBean("#EE6363", 7),
            ColorBean("#CD5555", 8))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diffutil)

        val diffAdapter = DiffUtilAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = diffAdapter

        diffAdapter.submitList(itemColorList)

        btDiff.setOnClickListener {
            val newColorList = mutableListOf<ColorBean>()
            newColorList.addAll(itemColorList)

            newColorList.removeAt(1)
            newColorList.removeAt(5)
            newColorList.add(3, ColorBean("#8B4726", 12))
            newColorList[2].color = "#FFD39B"

            diffAdapter.submitList(newColorList)
        }

        btRecover.setOnClickListener {
            diffAdapter.submitList(itemColorList)
        }
    }

    class DiffUtilAdapter(val context: Context) : RecyclerView.Adapter<MyViewHolder>() {

        //1. 声明 DiffUtil.ItemCallback 回调
        private val itemCallback = object : DiffUtil.ItemCallback<ColorBean>() {
            override fun areItemsTheSame(oldItem: ColorBean, newItem: ColorBean): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ColorBean, newItem: ColorBean): Boolean {
                return oldItem.color == newItem.color
            }
        }

        //2. 创建 AsyncListDiff 对象
        private val mDiffer = AsyncListDiffer<ColorBean>(this, itemCallback)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_diffutil, parent, false))
        }

        override fun getItemCount(): Int {
            return mDiffer.currentList.size
        }

        fun submitList(studentList: List<ColorBean>) {
            //3. 提交新数据列表
            mDiffer.submitList(studentList)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            //4. 从新数据列表中获取最新数据
            val bean = mDiffer.currentList[position]

            holder.tvColor.text = bean.color
            holder.itemView.setBackgroundColor(Color.parseColor(bean.color))
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvColor: TextView = view.findViewById(R.id.tvColor)
    }

    data class ColorBean(var color: String, val id: Int)
}