package com.sample.view.weights

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
    private val mPaint = Paint()

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
    private var leftDstWidth = 0f
    private var mLeftDstRectF: RectF? = null

    /**
     * 右滑块
     */
    private var mRightBitmap: Bitmap
    private var mRightSrcRect: Rect
    private var rightDstWidth = 0f
    private var mRightDstRectF: RectF? = null

    /**
     * 左右滑块滑动的距离
     */
    private var moveLeftRange = 0f
    private var moveRightRange = 0f

    /**
     * 最小(大)选择时间段(秒)
     */
    private var minSelectTime = MIN_SELECT_DURATION.toLong()
    private var maxSelectTime = MAX_SELECT_DURATION.toLong()

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
    private var selectStartTime: Long = 0
    private var selectEndTime: Long = 0

    /**
     * 进度条执行动画
     */
    private var progressAnima: ValueAnimator? = null

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
        mPaint.color = Color.WHITE
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
        viewMoveWidth = viewWidth - viewMarginSe * 2 - leftDstWidth - rightDstWidth

        //单位像素代表的时长
        unitTimeTange = maxSelectTime / viewMoveWidth
        minRange = MIN_SELECT_DURATION / unitTimeTange
        updateDstBitmapRect()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLACK)
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

        val bitmapHeight = viewHeight - viewMarginTb * 2
        leftDstWidth = mLeftSrcRect.width() / mLeftSrcRect.height().toFloat() * bitmapHeight
        rightDstWidth = mRightSrcRect.width() / mRightSrcRect.height().toFloat() * bitmapHeight

        //左右边界滑动限制
        if (moveLeftRange < 0) {
            moveLeftRange = 0f
        }
        if (moveRightRange < 0) {
            moveRightRange = 0f
        }
        val leftRectLeft = viewMarginSe + moveLeftRange
        val leftRectRight = leftRectLeft + leftDstWidth
        val rightRectLeft = viewWidth - viewMarginSe - rightDstWidth - moveRightRange
        val rightRectRgith = rightRectLeft + rightDstWidth

        //滑动选中最小范围限制
        if (minRange > viewWidth - (viewMarginSe * 2 + leftDstWidth + moveLeftRange + moveRightRange + rightDstWidth)) {
            return
        }

        //左右滑块绘制Rect
        mLeftDstRectF = RectF(leftRectLeft, viewMarginTb, leftRectRight, bitmapHeight + viewMarginTb)
        mRightDstRectF = RectF(rightRectLeft, viewMarginTb, rightRectRgith, bitmapHeight + viewMarginTb)

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
        val x = viewMarginSe + leftDstWidth + moveLeftRange
        progressPts[0] = x
        progressPts[1] = 0f
        progressPts[2] = x
        progressPts[3] = viewHeight.toFloat()

        if (null != rangeChangeListener) {
            selectStartTime = floor(unitTimeTange * moveLeftRange / 1000.toDouble()).toLong()
            selectEndTime = ceil(unitTimeTange * (viewMoveWidth - moveRightRange) / 1000.toDouble()).toLong()
            rangeChangeListener!!.onRangeValuesChanged(selectStartTime, selectEndTime)
        }
    }

    /**
     * 更新进度条动画
     */
    private fun updateProgressAnimation() {
        progressAnima = ValueAnimator.ofFloat(mLeftDstRectF!!.right, mRightDstRectF!!.left)
        progressAnima!!.addUpdateListener(AnimatorUpdateListener{ animation ->
            val progressValue = animation.animatedValue as Float
            updateProgressLine(progressValue)
            invalidate()
        })
        progressAnima!!.duration = (selectEndTime - selectStartTime) * 1000
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
    fun setVideoDuration(duration: Long) {
        //时长区间
        minSelectTime = 0
        if (duration < MAX_SELECT_DURATION) {
            maxSelectTime = duration
        }

        //初始选中开始播放时间和播放结束时间
        selectStartTime = minSelectTime
        selectEndTime = maxSelectTime
    }

    fun addRangeChangeListener(listener: OnRangeChangeListener?) {
        rangeChangeListener = listener
    }

    interface OnRangeChangeListener {
        fun onRangeValuesChanged(startTime: Long, maxValue: Long)
    }
}