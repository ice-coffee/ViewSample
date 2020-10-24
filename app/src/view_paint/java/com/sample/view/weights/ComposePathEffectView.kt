package com.sample.view.weights

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 *  @author mzp
 *  date : 2020/10/23
 *  desc :
 */
class ComposePathEffectView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val mPaint = Paint()
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 3f
        val mPath = Path()

        mPath.moveTo(200f, 200f)
        mPath.lineTo(400f, 200f)
        mPath.lineTo(400f, 400f)
        mPath.lineTo(200f, 400f)
        mPath.lineTo(200f, 200f)

        canvas!!.drawPath(mPath, mPaint)

        canvas.save()
        canvas.translate(width / 2f, 0f)

        mPath.reset()
        val rect = RectF(200f, 200f, 400f, 400f)
        mPath.addRect(rect, Path.Direction.CW)
        mPaint.pathEffect = ComposePathEffect(DashPathEffect(floatArrayOf(10f, 10f), 0f), CornerPathEffect(200f))
        canvas.drawPath(mPath, mPaint)
    }
}