package com.sample.view.appbarLayout

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.sample.view.R


/**
 * date: 2020/10/6
 * author: ice_coffee
 * remark:
 */
class SampleFiveFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_appbar_five, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

/**
 *
 * 自定义Behavior ：实现RecyclerView(或者其他可滑动View，如：NestedScrollView) 滑动覆盖header 的效果
 * Created by zhouwei on 16/12/19.
 */
class SampleFiveBehavior(context: Context, attributeSet: AttributeSet) : CoordinatorLayout.Behavior<View>(context, attributeSet) {

    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        val params = child.layoutParams as CoordinatorLayout.LayoutParams
        if (params.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
            child.layout(0, 0, parent.width, parent.height)
            child.translationY = headerHeight.toFloat()
            return true
        }
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed)
        // 在这个方法里面只处理向上滑动
        if (dy < 0) {
            return
        }
        val transY = child.translationY - dy
        if (transY > 0) {
            child.translationY = transY
            consumed[1] = dy
        }
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        // 在这个方法里只处理向下滑动
        if (dyUnconsumed > 0) {
            return
        }
        val transY = child.translationY - dyUnconsumed
        if (transY > 0 && transY < headerHeight) {
            child.translationY = transY
        }
    }

    /**
     * 获取Header 高度
     * @return
     */
    private val headerHeight: Int = context.resources.getDimensionPixelOffset(R.dimen.header_height)
}