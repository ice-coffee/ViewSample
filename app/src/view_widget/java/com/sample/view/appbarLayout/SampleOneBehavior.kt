package com.sample.view.appbarLayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
import androidx.recyclerview.widget.RecyclerView


/**
 *  @author mzp
 *  date : 2020/9/29
 *  desc :
 */
class SampleOneBehavior : Behavior<View?> {
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

        return true
    }
}