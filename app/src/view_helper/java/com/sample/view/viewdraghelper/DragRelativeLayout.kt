package com.sample.view.viewdraghelper

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import androidx.customview.widget.ViewDragHelper
import com.sample.common.utils.LogUtils
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 *  @author mzp
 *  date : 2020/9/28
 *  desc :
 */
class DragRelativeLayout : RelativeLayout {

    constructor(context: Context) : super(context){}
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet){}
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr){}

    /**
     * The list of [View]s that will be draggable.
     */
    private var mDragViews: MutableList<View>? = null

    /**
     * The [DragFrameLayoutController] that will be notify on drag.
     */
    private var mDragFrameLayoutController: DragFrameLayoutController? = null

    private lateinit var mDragHelper: ViewDragHelper
    var mCurrentLeft: Int = 0
    var mCurrentTop: Int = 0

    init {
        mDragViews = ArrayList()
        /**
         * Create the [ViewDragHelper] and set its callback.
         */
        mDragHelper = ViewDragHelper.create(this, 1.0f, object : ViewDragHelper.Callback() {
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
                    mDragHelper.settleCapturedViewAt(leftBound, mCurrentTop)
                } else {
                    mDragHelper.settleCapturedViewAt(rightBound, mCurrentTop)
                }
                invalidate()
            }

            override fun getViewHorizontalDragRange(child: View): Int {
                return measuredWidth - child.measuredWidth
            }

            override fun getViewVerticalDragRange(child: View): Int {
                return measuredHeight - child.measuredHeight
            }
        })
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mDragHelper.shouldInterceptTouchEvent(ev!!)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDragHelper.processTouchEvent(event!!)
        return super.onTouchEvent(event)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mDragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    /**
     * Adds a new [View] to the list of views that are draggable within the container.
     * @param dragView the [View] to make draggable
     */
    fun addDragView(dragView: View) {
        mDragViews!!.add(dragView)
    }

    /**
     * Sets the [DragFrameLayoutController] that will receive the drag events.
     * @param dragFrameLayoutController a [DragFrameLayoutController]
     */
    fun setDragFrameController(dragFrameLayoutController: DragFrameLayoutController?) {
        mDragFrameLayoutController = dragFrameLayoutController
    }

    /**
     * A controller that will receive the drag events.
     */
    interface DragFrameLayoutController {
        fun onDragDrop(captured: Boolean)
    }
}