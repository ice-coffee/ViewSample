package com.sample.view.weights

import android.animation.Animator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import com.sample.view.R

/**
 * @author mzp
 * date : 2020/11/2
 * desc :
 */
class NewRangeSeekBarView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    /**
     * 控件宽高
     */
    private var viewHeight = 0
    private var viewWidth = 0

    /**
     * 控件可滑动长度
     */
    private var viewMoveWidth = 0f

    /**
     * 控件 marginTop \ marginBottom
     */
    private val viewMarginTb = resources.getDimension(R.dimen.seek_margin_top_bottom)

    /**
     * 控件 marginStart \ marginEnd
     */
    private val viewMarginSe = resources.getDimension(R.dimen.seek_margin_start_end)

    /**
     * 边框宽度
     */
    private val viewStrokeWidth = resources.getDimension(R.dimen.seek_stroke_width)

    /**
     * 滑块宽高
     */
    private val slideWidth = resources.getDimension(R.dimen.slider_width)
    private val slideHeight = resources.getDimension(R.dimen.slider_height)

    private val mPaint = Paint()

    private val mBorderColor = Color.WHITE
    private val mShadowColor = resources.getColor(R.color.seek_shadow_color)

    /**
     * 上下两个线段的坐标
     */
    private val pts = FloatArray(8)

    /**
     * 进度条线段坐标
     */
    private val progressPts = FloatArray(4)

    /**
     * 左滑块
     */
    private var mLeftBitmap: Bitmap
    private var mLeftSrcRect: Rect
    private var mLeftDstRectF: RectF? = null

    private var mUnSelectLeftMargin = 0f
    private var mleftUnSelectRect: RectF? = null

    /**
     * 右滑块
     */
    private var mRightBitmap: Bitmap
    private var mRightSrcRect: Rect
    private var mRightDstRectF: RectF? = null

    private var mUnSelectRightMargin = 0f
    private var mRightUnSelectRect: RectF? = null

    private var bottomViewWidth = 0f

    /**
     * 最小时间段像素长度
     */
    private var minLength = 0f

    /**
     * 进度条执行动画
     */
    private var progressAnima: ValueAnimator? = null

    /**
     * 动画播放时长
     */
    private var animaDuration = 0f

    /**
     * 判断动画取消成因, true: 滑动滑块取消, false: 正常播放结束
     */
    private var isAnimationCancel = false

    /**
     * 当前拖动的是否为左滑块
     */
    private var isDragLeft = false

    /**
     * 当前拖动的是否为右滑块
     */
    private var isDragRight = false

    /**
     * 选中监听
     */
    private var rangeChangeListener: OnRangeChangeListener? = null
    private var gestureDetector: GestureDetector? = null

    private var isLeftMax = true
    private var isRightMax = true
    private var isMinLength = false
    /**
     * 手势滑动监听
     */
    private val gestureListener: SimpleOnGestureListener = object : SimpleOnGestureListener() {
        override fun onDown(event: MotionEvent): Boolean {
            isDragLeft = false
            isDragRight = false
            if (mLeftDstRectF!!.contains(event.rawX, event.y)) {
                isDragLeft = true
            }
            if (mRightDstRectF!!.contains(event.rawX, event.y)) {
                isDragRight = true
            }
            return isDragLeft || isDragRight
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            //当滑块选中范围缩小到最小值时不再处理可能缩小范围的滑动事件
            if (isMinLength) {
                if (isDragLeft && distanceX < 0) {
                    return true
                }
                if (isDragRight && distanceX > 0) {
                    return true
                }
                isMinLength = false
            } else {
                //当左滑块滑动到最左端不再处理左滑块左滑动事件
                if (isDragLeft && isLeftMax && distanceX > 0) {
                    return true
                } else {
                    isLeftMax = false
                }
                //当右滑块滑动到最右端不再处理右滑块右滑动事件
                if (isDragRight && isRightMax && distanceX < 0) {
                    return true
                } else {
                    isRightMax = false
                }
            }
            updateDstBitmapRect(distanceX)
            invalidate()
            return true
        }
    }

    init {
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = viewStrokeWidth
        mLeftBitmap = BitmapFactory.decodeResource(resources, R.mipmap.upload_btn_cut_left, )
        mRightBitmap = BitmapFactory.decodeResource(resources, R.mipmap.upload_btn_cut_right)

        mLeftSrcRect = Rect(0, 0, mLeftBitmap.width, mLeftBitmap.height)
        mRightSrcRect = Rect(0, 0, mRightBitmap.width, mRightBitmap.height)

        gestureDetector = GestureDetector(context, gestureListener)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewHeight = h
        viewWidth = w

        //左右滑块绘制Rect
        mLeftDstRectF = RectF(viewMarginSe, viewMarginTb, viewMarginSe + slideWidth, slideHeight + viewMarginTb)
        mRightDstRectF = RectF(viewWidth - viewMarginSe - slideWidth, viewMarginTb, viewWidth - viewMarginSe, slideHeight + viewMarginTb)

        mleftUnSelectRect = RectF(viewMarginSe + slideWidth, viewMarginTb + viewStrokeWidth, mLeftDstRectF!!.right, slideHeight + viewMarginTb - viewStrokeWidth)
        mRightUnSelectRect = RectF(mRightDstRectF!!.left, viewMarginTb + viewStrokeWidth, viewWidth - viewMarginSe - slideWidth, slideHeight + viewMarginTb - viewStrokeWidth)

        viewMoveWidth = viewWidth - viewMarginSe * 2 - slideWidth * 2

        mUnSelectRightMargin = bottomViewWidth - viewMoveWidth
        if (mUnSelectRightMargin < 0) {
            mUnSelectRightMargin = 0f
        }

        updateDstBitmapRect(0f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.color = mShadowColor
        canvas.drawRect(mleftUnSelectRect!!, mPaint)
        canvas.drawRect(mRightUnSelectRect!!, mPaint)

        mPaint.color = mBorderColor
        canvas.drawLines(pts, mPaint)
        canvas.drawLines(progressPts, mPaint)
        canvas.drawBitmap(mLeftBitmap, mLeftSrcRect, mLeftDstRectF!!, mPaint)
        canvas.drawBitmap(mRightBitmap, mRightSrcRect, mRightDstRectF!!, mPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val isHandle = gestureDetector!!.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_UP) {
            updateProgressAnimation()
        }
        return isHandle
    }

    /**
     * 绘制更新
     */
    private fun updateDstBitmapRect(dx: Float) {
        //取消动画
        progressAnima?.cancel()

        var distanceX = dx
        //左右边界滑动限制
        if (isDragLeft) {
            //滑动选中最小范围限制
            if (minLength > mRightDstRectF!!.left - mLeftDstRectF!!.right + distanceX) {
                //当最后一次滑动超过最小长度时, 控制移动距离使其正好等于最小长度
                distanceX = minLength - (mRightDstRectF!!.left - mLeftDstRectF!!.right)
                isMinLength = true
            }
            var rectLeft = mLeftDstRectF!!.left
            rectLeft -= distanceX
            if (rectLeft <= viewMarginSe) {
                rectLeft = viewMarginSe
                //当最后一次滑动超过最左侧时, 控制移动距离使其正好停在最左侧
                distanceX = mLeftDstRectF!!.left - viewMarginSe
                isLeftMax = true
            }
            mLeftDstRectF!!.left = rectLeft
            mLeftDstRectF!!.right = rectLeft + slideWidth
        }
        if (isDragRight) {
            //滑动选中最小范围限制
            if (minLength > mRightDstRectF!!.left - mLeftDstRectF!!.right - distanceX) {
                distanceX = mRightDstRectF!!.left - mLeftDstRectF!!.right - minLength
                isMinLength = true
            }
            var rectRight = mRightDstRectF!!.right
            rectRight -= distanceX
            if (rectRight >= viewWidth - viewMarginSe) {
                rectRight = viewWidth - viewMarginSe
                //当最后一次滑动超过最右侧时, 控制移动距离使其正好停在最右侧
                distanceX = mRightDstRectF!!.right - (viewWidth - viewMarginSe)
                isRightMax = true
            }
            mRightDstRectF!!.right = rectRight
            mRightDstRectF!!.left = rectRight - slideWidth
        }

        //获取动画执行时长
        if (null != rangeChangeListener) {
            animaDuration = rangeChangeListener!!.onRangeValuesChanged(distanceX, isDragLeft)
        }

        val leftRectRight = mLeftDstRectF!!.right
        val rightRectLeft = mRightDstRectF!!.left

        var unSelectRectLeft = viewMarginSe + slideWidth - mUnSelectLeftMargin
        if (unSelectRectLeft < 0) {
            unSelectRectLeft = 0f
        }
        var unSelectRectRight = viewWidth - viewMarginSe - slideWidth + mUnSelectRightMargin
        if (unSelectRectRight > viewWidth) {
            unSelectRectRight = viewWidth.toFloat()
        }
        mleftUnSelectRect!!.left = unSelectRectLeft
        mleftUnSelectRect!!.right = leftRectRight
        mRightUnSelectRect!!.left = rightRectLeft
        mRightUnSelectRect!!.right = unSelectRectRight

        //上下两条边线绘制坐标
        pts[0] = leftRectRight
        pts[1] = viewMarginTb + viewStrokeWidth / 2
        pts[2] = rightRectLeft
        pts[3] = viewMarginTb + viewStrokeWidth / 2
        pts[4] = leftRectRight
        pts[5] = viewHeight - viewMarginTb - viewStrokeWidth / 2
        pts[6] = rightRectLeft
        pts[7] = viewHeight - viewMarginTb - viewStrokeWidth / 2

        //进度条初始位置
        progressPts[0] = mLeftDstRectF!!.right
        progressPts[1] = 0f
        progressPts[2] = mLeftDstRectF!!.right
        progressPts[3] = viewHeight.toFloat()
    }

    /**
     * 更新进度条动画
     */
    private fun updateProgressAnimation() {
        progressAnima = ValueAnimator.ofFloat(mLeftDstRectF!!.right, mRightDstRectF!!.left)
        progressAnima!!.addUpdateListener(AnimatorUpdateListener { animation ->
            val progressValue = animation.animatedValue as Float
            updateProgressLine(progressValue)
            invalidate()
        })
        progressAnima!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                if (!isAnimationCancel) {
                    rangeChangeListener?.onPlayEnd()
                    updateProgressAnimation()
                } else {
                    isAnimationCancel = false
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
                isAnimationCancel = true
            }

            override fun onAnimationStart(animation: Animator?) {
                rangeChangeListener?.onPlayStart()
            }
        })
        progressAnima!!.duration = animaDuration.toLong()
        progressAnima!!.start()
    }

    /**
     * 进度条位置更新
     */
    private fun updateProgressLine(progressValue: Float) {
        progressPts[0] = progressValue
        progressPts[1] = 0f
        progressPts[2] = progressValue
        progressPts[3] = viewHeight.toFloat()
    }

    fun initBottomShadow(bottomViewWidth: Float) {
        this.bottomViewWidth = bottomViewWidth
    }

    fun initMinLength(length: Float) {
        this.minLength = length
    }

    fun updateUnSelectRect(dx: Int) {

        if (dx != 0) {
            mUnSelectLeftMargin += dx
            mUnSelectRightMargin -= dx
            updateDstBitmapRect(0f)
            invalidate()
        }
    }

    fun addRangeChangeListener(listener: OnRangeChangeListener?) {
        rangeChangeListener = listener
    }

    interface OnRangeChangeListener {
        fun onPlayStart()
        fun onRangeValuesChanged(dx: Float, isLeft: Boolean): Float
        fun onPlayEnd()
    }
}