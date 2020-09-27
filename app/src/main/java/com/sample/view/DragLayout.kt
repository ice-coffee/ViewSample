package com.sample.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import androidx.customview.widget.ViewDragHelper
import com.sample.common.utils.LogUtils
import kotlin.math.max
import kotlin.math.min

/**
 * date: 2020/9/27
 * author: ice_coffee
 * remark:
 */
public class DragLayout(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    private var mDragHelper: ViewDragHelper? = null
    var mCurrentLeft: Int = 0
    var mCurrentTop: Int = 0

    init {
        mDragHelper = ViewDragHelper.create(this, object: ViewDragHelper.Callback(){
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return true
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                val minLeft = paddingLeft
                val maxLeft = width - child.width - paddingRight
                mCurrentLeft = min(max(minLeft, left), maxLeft)
                return mCurrentLeft
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                val minTop = paddingTop
                val maxTop = height - child.height - paddingBottom
                mCurrentTop = min(max(minTop, top), maxTop)
                return mCurrentTop
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                super.onViewReleased(releasedChild, xvel, yvel)

                val leftBound = paddingLeft
                val rightBound = width - releasedChild.width - paddingRight

                LogUtils.e("$leftBound, $rightBound, $mCurrentLeft, $mCurrentTop")
                if ((releasedChild.width / 2 + mCurrentLeft) < width / 2) {
                    mDragHelper?.settleCapturedViewAt(leftBound, mCurrentTop)
                } else {
                    mDragHelper?.settleCapturedViewAt(rightBound, mCurrentTop)
                }
                invalidate()
            }
        })
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mDragHelper!!.shouldInterceptTouchEvent(ev!!)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDragHelper?.processTouchEvent(event!!)
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (null != mDragHelper && mDragHelper!!.continueSettling(true)) {
            invalidate()
        }
    }
}