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
import kotlin.math.ceil
import kotlin.math.floor

/**
 * @author mzp
 * date : 2020/11/2
 * desc :
 */
class NewRangeSeekBarView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val MIN_SELECT_DURATION = 3 * 1000
    private val MAX_SELECT_DURATION = 60 * 1000

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
     * 左右滑块滑动的距离
     */
    private var moveLeftRange = 0f
    private var moveRightRange = 0f

    /**
     * 最小(大)选择时间段(毫秒)
     */
    private var minSelectTime = MIN_SELECT_DURATION
    private var maxSelectTime = MAX_SELECT_DURATION

    /**
     * 最小时间段像素长度
     */
    private var minRange = 0f

    /**
     * 单位 PX  = 多少时长
     */
    private var unitTimeTange = 0f

    /**
     * 选中时间线(秒)
     */
    private var selectStartTime = 0
    private var selectEndTime = 0

    /**
     * 进度条执行动画
     */
    private var progressAnima: ValueAnimator? = null

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
            if (isDragLeft) {
                moveLeftRange -= distanceX
            }
            if (isDragRight) {
                moveRightRange += distanceX
            }
            updateDstBitmapRect()
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
        viewMoveWidth = viewWidth - viewMarginSe * 2 - slideWidth - slideWidth

        mUnSelectRightMargin = bottomViewWidth - viewWidth

        //单位像素代表的时长
        unitTimeTange = maxSelectTime / viewMoveWidth
        minRange = MIN_SELECT_DURATION / unitTimeTange
        updateDstBitmapRect()
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
    private fun updateDstBitmapRect() {

        //取消动画
        progressAnima?.cancel()

        //左右边界滑动限制
        if (moveLeftRange < 0) {
            moveLeftRange = 0f
        }
        if (moveRightRange < 0) {
            moveRightRange = 0f
        }
        val leftRectLeft = viewMarginSe + moveLeftRange
        val leftRectRight = leftRectLeft + slideWidth
        val rightRectLeft = viewWidth - viewMarginSe - slideWidth - moveRightRange
        val rightRectRgith = rightRectLeft + slideWidth

        //滑动选中最小范围限制
        if (minRange > viewWidth - (viewMarginSe * 2 + slideWidth + moveLeftRange + moveRightRange + slideWidth)) {
            return
        }

        //左右滑块绘制Rect
        mLeftDstRectF = RectF(leftRectLeft, viewMarginTb, leftRectRight, slideHeight + viewMarginTb)
        mRightDstRectF = RectF(rightRectLeft, viewMarginTb, rightRectRgith, slideHeight + viewMarginTb)

        var unSelectRectLeft = leftRectRight - moveLeftRange - mUnSelectLeftMargin
        var unSelectRectRight = rightRectLeft + moveRightRange + mUnSelectRightMargin
        if (unSelectRectLeft < 0) {
            unSelectRectLeft = 0f
        }
        if (unSelectRectRight > viewWidth) {
            unSelectRectRight = viewWidth.toFloat()
        }
        mleftUnSelectRect = RectF(unSelectRectLeft, viewMarginTb + viewStrokeWidth, leftRectRight, slideHeight + viewMarginTb - viewStrokeWidth)
        mRightUnSelectRect = RectF(rightRectLeft, viewMarginTb + viewStrokeWidth, unSelectRectRight, slideHeight + viewMarginTb - viewStrokeWidth)

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
        val x = viewMarginSe + slideWidth + moveLeftRange
        progressPts[0] = x
        progressPts[1] = 0f
        progressPts[2] = x
        progressPts[3] = viewHeight.toFloat()

        if (null != rangeChangeListener) {
            selectStartTime = floor(unitTimeTange * moveLeftRange).toInt()
            selectEndTime = ceil(unitTimeTange * (viewMoveWidth - moveRightRange)).toInt()
            rangeChangeListener!!.onRangeValuesChanged(selectStartTime, selectEndTime)
        }
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
        progressAnima!!.duration = (selectEndTime - selectStartTime).toLong()
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

    /**
     * 根据视频时长初始化选择框可选区间
     * @param duration 视频时长,单位毫秒
     */
    fun setVideoDuration(duration: Int) {
        //时长区间
        minSelectTime = 0
        if (duration < MAX_SELECT_DURATION) {
            maxSelectTime = duration
        }

        //初始选中开始播放时间和播放结束时间
        selectStartTime = minSelectTime
        selectEndTime = maxSelectTime

        updateProgressAnimation()
    }

    fun initBottomShadow(bottomViewWidth: Float) {
        this.bottomViewWidth = bottomViewWidth
    }

    fun updateUnSelectRect(dx: Int) {

        mUnSelectLeftMargin += dx
        mUnSelectRightMargin -= dx
        updateDstBitmapRect()
        invalidate()
    }

    fun addRangeChangeListener(listener: OnRangeChangeListener?) {
        rangeChangeListener = listener
    }

    interface OnRangeChangeListener {
        fun onPlayStart()
        fun onRangeValuesChanged(startTime: Int, endTime: Int)
        fun onPlayEnd()
    }
}