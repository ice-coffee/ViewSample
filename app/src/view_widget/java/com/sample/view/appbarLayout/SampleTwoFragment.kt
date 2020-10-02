package com.sample.view.appbarLayout

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.common.utils.LogUtils
import com.sample.view.R
import kotlinx.android.synthetic.main.fragment_appbar_one.*


/**
 *  @author mzp
 *  date : 2020/9/29
 *  desc :
 */
class SampleTwoFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_appbar_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myList.layoutManager = LinearLayoutManager(context)
        myList.adapter = MyAdapter(context!!)
    }
}

class SampleHeaderBehavior : CoordinatorLayout.Behavior<TextView> {
    // 界面整体向上滑动，达到列表可滑动的临界点
    private var upReach = false

    // 列表向上滑动后，再向下滑动，达到界面整体可滑动的临界点
    private var downReach = false

    // 列表上一个全部可见的item位置
    private var lastPosition = -1

    constructor() {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: TextView, ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downReach = false
                upReach = false
            }
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: TextView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: TextView, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if (target is RecyclerView) {
            // 列表第一个全部可见Item的位置
            val pos = (target.layoutManager as LinearLayoutManager?)!!.findFirstCompletelyVisibleItemPosition()
            if (pos == 0 && pos < lastPosition) {
                downReach = true
            }
            // 整体可以滑动，否则RecyclerView消费滑动事件
            if (canScroll(child, dy.toFloat()) && pos == 0) {
                LogUtils.e("${child.translationY}, $dy")
                var finalY = child.translationY - dy
                if (finalY < -child.height) {
                    finalY = -child.height.toFloat()
                    upReach = true
                } else if (finalY > 0) {
                    finalY = 0f
                }
                child.translationY = finalY
                // 让CoordinatorLayout消费滑动事件
                consumed[1] = dy
            }
            lastPosition = pos
        }
    }

    private fun canScroll(child: View, scrollY: Float): Boolean {
        if (scrollY > 0 && child.translationY == -child.height.toFloat() && !upReach) {
            return false
        }
        return !downReach
    }
}

class RecyclerViewBehavior : CoordinatorLayout.Behavior<RecyclerView> {
    constructor() {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun layoutDependsOn(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        return dependency is TextView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        //计算列表y坐标，最小为0
        var y = dependency.height + dependency.translationY
        if (y < 0) {
            y = 0f
        }
        child.y = y
        return true
    }
}