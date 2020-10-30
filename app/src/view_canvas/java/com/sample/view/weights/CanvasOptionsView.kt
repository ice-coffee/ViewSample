package com.sample.view.weights

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 *  @author mzp
 *  date : 2020/10/30
 *  desc :
 */
class CanvasOptionsView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()

    private var viewHeight = 0
    private var viewWidth = 0

    private var currentRotate = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewHeight = h
        viewWidth = w

        val valueAnimator = ValueAnimator.ofInt(0, 360)
        valueAnimator.addUpdateListener { animation ->
            currentRotate = animation.animatedValue as Int
            invalidate()
        }
        valueAnimator.duration = 5000
        valueAnimator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.rotate(currentRotate.toFloat(), viewWidth / 2f, viewHeight / 2f)
        canvas.drawText("CanvasOptionsView", viewWidth / 2f, viewHeight / 2f, paint)
    }
}