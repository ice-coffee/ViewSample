package com.sample.view.weights

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 *  @author mzp
 *  date : 2020/10/23
 *  desc :
 */
class DashPathEffectView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val mPaint = Paint()
    private var mPath = Path()

    private var viewHeight = 0

    init {
        mPaint.strokeWidth = 20f
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeJoin = Paint.Join.ROUND

        val animator = ValueAnimator.ofFloat(0f, 200f)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            val currentValue = animation.animatedValue as Float

            //绘制 Path 更加平滑
            mPaint.pathEffect = DashPathEffect(floatArrayOf(200f, 100f), currentValue)
            postInvalidate()
        }
        animator.repeatMode = ValueAnimator.RESTART

        animator.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewHeight = h

        mPath.moveTo(0f, h / 2f)
        mPath.lineTo(w.toFloat(), h / 2f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 绘制原始路径
        canvas!!.save()
        canvas.drawPath(mPath, mPaint)
        canvas.restore()
    }
}