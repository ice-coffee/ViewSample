package com.sample.view.weights

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * date: 2020/10/23
 * author: ice_coffee
 * remark: 介绍 FontMetrics \ measureText \ getTextBounds 的用法, 和字符绘制时的坐标问题
 */
class TextMeasureView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val text = "MfgiA"
    private var viewHeight = 0
    private var viewWidth = 0

    private val paddingTop = 400f
    private val paddingLeft = 200f

    private val paint = Paint()

    init {
        paint.textSize = 200f
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewHeight = h
        viewWidth = w
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        /**
         * 标注绘制区域
         */
        canvas!!.saveLayer(RectF(0f, 0f, viewWidth.toFloat(), viewHeight / 2f), paint)
        canvas.drawColor(Color.BLACK)
        canvas.restore()

        /**
         * 移动 canvas (也就是移动坐标系)
         */
        canvas.translate(paddingLeft, paddingTop)

        /**
         * 绘制坐标系
         */
        paint.strokeWidth = 4f
        paint.color = Color.WHITE
        canvas.drawLine(0f, -paddingTop, 0f, viewHeight.toFloat() / 2 - paddingTop, paint)
        val yArrowPath = Path()
        yArrowPath.moveTo(0f, viewHeight.toFloat() / 2 - paddingTop)
        yArrowPath.lineTo(0f - 20, viewHeight.toFloat() / 2 - 20 - paddingTop)
        yArrowPath.lineTo(0f + 20, viewHeight.toFloat() / 2 - 20 - paddingTop)
        yArrowPath.lineTo(0f, viewHeight.toFloat() / 2 - paddingTop)
        canvas.drawPath(yArrowPath, paint)

        canvas.drawLine(-paddingLeft, 0f, viewWidth.toFloat() - paddingLeft, 0f, paint)
        val xArrowPath = Path()
        xArrowPath.moveTo(viewWidth.toFloat() - paddingLeft, 0f)
        xArrowPath.lineTo(viewWidth.toFloat() - paddingLeft - 20, 0f - 20)
        xArrowPath.lineTo(viewWidth.toFloat() - paddingLeft - 20, 0f + 20)
        xArrowPath.lineTo(viewWidth.toFloat() - paddingLeft, 0f)
        canvas.drawPath(xArrowPath, paint)

        /**
         * measureText 测量文字宽度
         */
        val textWidth = paint.measureText(text, 0, text.length)

        /**
         * getTextBounds 测量文字宽高
         */
        val textRect = Rect()
        paint.getTextBounds(text, 0, text.length, textRect)

        /**
         * 测量文字各个值
         */
        val fontMetrics = paint.fontMetrics

        /**
         * 根据 measureText 绘制文字范围
         */
        val textPath = Path()
        paint.color = Color.GREEN
        textPath.addRect(RectF(textRect.left.toFloat(), textRect.top.toFloat(), textWidth + textRect.left.toFloat(), textRect.bottom.toFloat()), Path.Direction.CW)
        canvas.drawPath(textPath, paint)

        /**
         * 根据 getTextBounds 绘制文字范围
         */
        paint.color = Color.RED
        textPath.reset()
        textPath.addRect(RectF(textRect.left.toFloat(), textRect.top.toFloat(), textRect.right.toFloat(), textRect.bottom.toFloat()), Path.Direction.CW)
        canvas.drawPath(textPath, paint)

        /**
         * 从上到下依次绘制fontMetrics的测量结果
         */
        paint.strokeWidth = 10f
        paint.color = Color.WHITE
        canvas.drawLine(0f, fontMetrics.top, textWidth, fontMetrics.top, paint)
        paint.color = Color.RED
        canvas.drawLine(0f, fontMetrics.ascent, textWidth, fontMetrics.ascent, paint)
        paint.color = Color.BLUE
        canvas.drawLine(0f, fontMetrics.descent, textWidth, fontMetrics.descent, paint)
        paint.color = Color.WHITE
        canvas.drawLine(0f, fontMetrics.bottom, textWidth, fontMetrics.bottom, paint)

        /**
         * 绘制原点
         */
        paint.color = Color.GREEN
        paint.strokeWidth = 20f
        canvas.drawPoint(0f, 0f, paint)

        /**
         * 绘制文字
         */
        paint.color = Color.WHITE
        canvas.drawText(text, 0f, 0f, paint)
    }
}