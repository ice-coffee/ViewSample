package com.sample.view.appbarLayout

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.common.utils.dp2px
import com.sample.view.R
import kotlinx.android.synthetic.main.fragment_appbar_one.*


/**
 *  @author mzp
 *  date : 2020/9/29
 *  desc :
 */
class SampleTwoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_appbar_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myList.layoutManager = LinearLayoutManager(context)
        myList.adapter = MyAdapter(context!!)
    }
}

class SampleLinearBehavior : CoordinatorLayout.Behavior<LinearLayout> {

    private val maxY = dp2px(200).toFloat()
    private val minY = dp2px(60).toFloat()

    constructor() {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: LinearLayout, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: LinearLayout, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (target is RecyclerView) {
            val layoutManager = target.layoutManager
            if (layoutManager is LinearLayoutManager) {
                val pos = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (pos == 0) {
                    var customY = 0f
                    var finalY = child.translationY - dy
                    if (finalY > minY) {
                        if (finalY > maxY) {
                            finalY = maxY
                        }
                        customY = dy.toFloat()
                    } else {
                        if (child.translationY > minY) {
                            customY = child.translationY - minY
                        } else {
                            customY = 0f
                        }
                        finalY = minY
                    }
                    consumed[1] = customY.toInt()
                    child.translationY = finalY
                }
            }
        }
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: LinearLayout, layoutDirection: Int): Boolean {
        child.translationY = maxY
        return false
    }
}

class SampleTextViewBehavior : CoordinatorLayout.Behavior<TextView> {

    private val minY = dp2px(60).toFloat()

    constructor() {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun layoutDependsOn(parent: CoordinatorLayout, child: TextView, dependency: View): Boolean {
        return dependency is LinearLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: TextView, dependency: View): Boolean {

        child.visibility = if (dependency.translationY == minY) View.VISIBLE else View.INVISIBLE
        return true
    }
}

class SampleHeaderBehavior : CoordinatorLayout.Behavior<ImageView> {

    constructor() {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: ImageView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

//    override fun onLayoutChild(parent: CoordinatorLayout, child: ImageView, layoutDirection: Int): Boolean {
//        Log.e("behavior", "onLayoutChild")
//        return super.onLayoutChild(parent, child, layoutDirection)
//    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: ImageView, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if (target is RecyclerView) {
            val layoutManager = target.layoutManager
            if (layoutManager is LinearLayoutManager) {
                val pos = layoutManager.findFirstCompletelyVisibleItemPosition()

                if (pos == 0) {
                    var customY = 0f
                    var finalY = child.translationY - dy
                    if (finalY > -child.height && finalY < 0) {
                        customY = dy.toFloat()
                    } else if (finalY < -child.height) {
                        customY = finalY - (-child.height)
                        finalY = -child.height.toFloat()
                    } else if (finalY > 0) {
                        customY = finalY - 0
                        finalY = 0f
                    }

                    consumed[1] = customY.toInt()
                    child.translationY = finalY
                }
            }
        }
    }
}

class RecyclerViewBehavior : CoordinatorLayout.Behavior<RecyclerView> {
    constructor() {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun layoutDependsOn(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        return dependency is ImageView
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