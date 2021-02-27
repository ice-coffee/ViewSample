package com.sample.view.weights

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 *  @author mzp
 *  date : 2020/10/23
 *  desc : 通过 getFillPath 获取 Path 绘制图形的外形边框
 */
class GetFillPathView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val arcPath = Path()

        arcPath.addArc(RectF(100f, 100f, 500f, 500f), 30f, 300f)

        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 100f

        canvas!!.drawPath(arcPath, paint)

        canvas.translate(0f, 500f)

        //获取
        val dstPath = Path()
        paint.getFillPath(arcPath, dstPath)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        paint.isAntiAlias = true
        canvas.drawPath(dstPath, paint)
    }
}