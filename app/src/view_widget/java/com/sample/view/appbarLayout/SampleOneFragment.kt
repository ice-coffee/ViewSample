package com.sample.view.appbarLayout

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.sample.common.utils.LogUtils
import com.sample.view.R
import kotlinx.android.synthetic.main.fragment_appbar_one.*
import java.util.*

/**
 *  @author mzp
 *  date : 2020/9/29
 *  desc :
 */
class SampleOneFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_appbar_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myList.layoutManager = LinearLayoutManager(context)
        myList.adapter = MyAdapter(context!!)
    }

    class SampleOneBehavior : CoordinatorLayout.Behavior<View?> {
        // 列表顶部和title底部重合时，列表的滑动距离。
        private var deltaY = 0f

        constructor() {}
        constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

        override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
            return dependency is RecyclerView
        }

        override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
            if (deltaY == 0f) {
                deltaY = dependency.y - child.height
            }

            var dy: Float = dependency.y - child.height
            dy = if (dy < 0) 0f else dy
            val y = -(dy / deltaY) * child.height
            child.translationY = y
            LogUtils.e(y)

            /**
             * 透明度变化
             * val y = 1 - (dy / deltaY)
             * child.alpha = y
             */

            return true
        }

        override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
            LogUtils.e("onLayoutChild")
            return super.onLayoutChild(parent, child, layoutDirection)
        }
    }
}

class MyAdapter(context: Context) : Adapter<MyAdapter.MyViewHolder>() {

    private val colorArray = arrayOf("#FADB14", "#FFD6E7", "#D9D9D9", "#BAE7FF", "#D3F261", "#BAC5F3", "#FFEADB", "#FF847C", "#D9F7BE", "#FFEECC")
    private val random = Random()
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        return MyAdapter.MyViewHolder(layoutInflater.inflate(R.layout.view_item, parent, false))
    }

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        holder.tvItem.text = "item$position"
        holder.tvItem.setBackgroundColor(Color.parseColor(colorArray[random.nextInt(colorArray.size)]))
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvItem: TextView = view.findViewById(R.id.tvItem)
    }
}