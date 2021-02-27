package com.sample.view.viewdraghelper

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.customview.widget.ViewDragHelper
import com.sample.common.utils.LogUtils


/**
 *  @author mzp
 *  date : 2020/9/28
 *  desc :
 */
class BottomMenuLayout: LinearLayout {

    constructor(context: Context) : super(context){}
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet){}
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr){}

    private var mDragHelper: ViewDragHelper? = null
    private var mContent: View? = null
    private var mBottomMenu: View? = null

    init {
        orientation = VERTICAL
        val callback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return child === mBottomMenu
            }

            override fun onEdgeTouched(edgeFlags: Int, pointerId: Int) {
                super.onEdgeTouched(edgeFlags, pointerId)
            }

            override fun onEdgeLock(edgeFlags: Int): Boolean {
                return super.onEdgeLock(edgeFlags)
            }

            override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
                mDragHelper!!.captureChildView(mBottomMenu!!, pointerId)
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                return Math.max(height - child.getHeight(), top)
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                LogUtils.e("$xvel, $yvel")
                if (yvel <= 0) {
                    mDragHelper!!.settleCapturedViewAt(0,
                            height - releasedChild.getHeight())
                } else {
                    mDragHelper!!.settleCapturedViewAt(0, height)
                }
                invalidate()
            }
        }
        mDragHelper = ViewDragHelper.create(this, callback)
        // 触发边缘为下边缘
        mDragHelper?.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // 假设第一个子 view 是内容区域，第二个是菜单
        mContent = getChildAt(0)
        mBottomMenu = getChildAt(1)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mDragHelper != null && mDragHelper!!.continueSettling(true)) {
            invalidate()
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mDragHelper!!.shouldInterceptTouchEvent(ev!!)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDragHelper!!.processTouchEvent(event!!)
        return true
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (mBottomMenu != null && mContent != null) {
            mBottomMenu!!.layout(0, height, mBottomMenu!!.measuredWidth, height + mBottomMenu!!.measuredHeight)
            mContent!!.layout(0, 0, mContent!!.measuredWidth, mContent!!.measuredHeight)
        }
    }
}