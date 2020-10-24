package com.sample.view.weights

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.sample.common.utils.LogUtils

/**
 * date: 2020/10/23
 * author: ice_coffee
 * remark: 介绍 FontMetrics \ measureText \ getTextBounds 的用法, 和字符绘制时的坐标问题
 */
class TextMeasureView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val text = "ABC"
    private var viewHeight = 0
    private var viewWidth = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewHeight = h
        viewWidth = w

        LogUtils.e("$viewHeight, $viewWidth")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint()
        paint.textSize = 200f
        paint.strokeCap = Paint.Cap.ROUND
        canvas!!.drawText(text, 0f, 0f, paint)

        canvas.saveLayer(RectF(0f, 0f, viewWidth.toFloat(), viewHeight / 2f), paint)
        canvas.drawColor(Color.BLACK)

        val paddingTop = 200f
        val paddingLeft = 200f

        val textWidth = paint.measureText(text, 0, text.length)
        val textRect = Rect()
        paint.getTextBounds(text, 0, text.length, textRect)

        val fontMetrics = paint.fontMetrics

        //坐标轴
        paint.strokeWidth = 4f
        paint.color = Color.WHITE
        canvas.drawLine(0f, paddingTop + (-fontMetrics.ascent), viewWidth.toFloat(), paddingTop + (-fontMetrics.ascent), paint)
        canvas.drawLine(paddingLeft, 0f, paddingLeft, viewHeight.toFloat(), paint)
        val yArrowPath = Path()
        yArrowPath.moveTo(paddingLeft, viewHeight.toFloat() / 2)
        yArrowPath.lineTo(paddingLeft - 20, viewHeight.toFloat() / 2 - 20)
        yArrowPath.lineTo(paddingLeft + 20, viewHeight.toFloat() / 2 - 20)
        yArrowPath.lineTo(paddingLeft, viewHeight.toFloat() / 2)
        canvas.drawPath(yArrowPath, paint)
        val xArrowPath = Path()
        xArrowPath.moveTo(viewWidth.toFloat(), paddingTop + (-fontMetrics.ascent))
        xArrowPath.lineTo(viewWidth.toFloat() - 20, paddingTop + (-fontMetrics.ascent) - 20)
        xArrowPath.lineTo(viewWidth.toFloat() - 20, paddingTop + (-fontMetrics.ascent) + 20)
        xArrowPath.lineTo(viewWidth.toFloat(), paddingTop + (-fontMetrics.ascent))
        canvas.drawPath(xArrowPath, paint)

        paint.strokeWidth = 10f

        //measureText\getTextBounds测量文字宽度
        canvas.save()
        canvas.translate(paddingLeft, paddingTop - fontMetrics.ascent)
        val textPath = Path()
        paint.color = Color.GREEN
        textPath.addRect(RectF(textRect.left.toFloat(), textRect.top.toFloat(), textWidth - textRect.left.toFloat(), textRect.bottom.toFloat()), Path.Direction.CW)
        canvas.drawPath(textPath, paint)
        paint.color = Color.RED
        textPath.reset()
        textPath.addRect(RectF(textRect.left.toFloat(), textRect.top.toFloat(), textRect.right.toFloat(), textRect.bottom.toFloat()), Path.Direction.CW)
        canvas.drawPath(textPath, paint)
        canvas.restore()

        //fontMetrics的各种测量结果
        paint.color = Color.YELLOW
        canvas.drawLine(paddingLeft, paddingTop, paddingLeft + textWidth, paddingTop, paint)
        paint.color = Color.RED
        canvas.drawLine(paddingLeft, paddingTop + (-fontMetrics.ascent), paddingLeft + textWidth, paddingTop + (-fontMetrics.ascent), paint)
        paint.color = Color.BLUE
        canvas.drawLine(paddingLeft, paddingTop + (-fontMetrics.ascent) + fontMetrics.descent, paddingLeft + textWidth, paddingTop + (-fontMetrics.ascent) + fontMetrics.descent, paint)
        paint.color = Color.GREEN
        paint.strokeWidth = 20f
        canvas.drawPoint(paddingLeft, paddingTop + (-fontMetrics.ascent), paint)

        paint.color = Color.WHITE
        canvas.drawText(text, 0f + paddingLeft, -fontMetrics.ascent + paddingTop, paint)

        canvas.restore()
    }
}