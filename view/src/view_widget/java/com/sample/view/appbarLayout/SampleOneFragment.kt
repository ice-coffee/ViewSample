package com.sample.view.appbarLayout

import android.content.Context
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
import com.sample.view.R
import kotlinx.android.synthetic.main.fragment_appbar_one.*

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

    class SampleOneBehavior : CoordinatorLayout.Behavior<TextView> {
        // 列表顶部和title底部重合时，列表的滑动距离。
        private var deltaY = 0f

        constructor() {}
        constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

        override fun layoutDependsOn(parent: CoordinatorLayout, child: TextView, dependency: View): Boolean {
            return dependency is RecyclerView
        }

        override fun onDependentViewChanged(parent: CoordinatorLayout, child: TextView, dependency: View): Boolean {
            if (deltaY == 0f) {
                deltaY = dependency.y - child.height
            }

            var dy: Float = dependency.y - child.height
            dy = if (dy < 0) 0f else dy
            val y = -(dy / deltaY) * child.height
            child.translationY = y

            /**
             * 透明度变化
             * val y = 1 - (dy / deltaY)
             * child.alpha = y
             */

            return true
        }
    }
}