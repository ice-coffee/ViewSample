package com.sample.view.appbarLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.behavior.SwipeDismissBehavior
import com.sample.common.utils.LogUtils
import com.sample.view.R
import kotlinx.android.synthetic.main.fragment_appbar_four.*


/**
 * date: 2020/10/3
 * author: ice_coffee
 * remark:
 */
class SampleFourFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_appbar_four, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipe = SwipeDismissBehavior<TextView>()
        /**
         * 设置滑动的方向，有3个值
         *
         * 1，SWIPE_DIRECTION_ANY 表示向左像右滑动都可以，
         * 2，SWIPE_DIRECTION_START_TO_END，只能从左向右滑
         * 3，SWIPE_DIRECTION_END_TO_START，只能从右向左滑
         */
        swipe.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)
        /**
         * 设置修改滑动组件透明度前滑动的最小距离
         */
        swipe.setStartAlphaSwipeDistance(0f)
        /**
         * 检测滑动开始的灵敏度
         */
        swipe.setSensitivity(0.2f)
        swipe.listener = object : SwipeDismissBehavior.OnDismissListener {
            override fun onDismiss(view: View) {
                LogUtils.e("------>onDissmiss")
            }

            override fun onDragStateChanged(state: Int) {
                LogUtils.e("------>onDragStateChanged")
            }
        }

        val layoutParams = swipLayout.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = swipe
    }
}