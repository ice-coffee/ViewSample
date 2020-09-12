package com.sample.view.weights

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * @date: 2020/9/12
<p>
 * @author: ice_coffee
<p>
 * @remark:
 */
class CanvasView : View {

    lateinit var mPaint: Paint

    constructor(context: Context):super(context){ initView() }
    constructor(context: Context, attributes: AttributeSet?):super(context, attributes){ initView() }
    constructor(context: Context, attributes: AttributeSet?, defStyleAttr: Int):super(context, attributes, defStyleAttr){ initView() }

    private fun initView() {
        mPaint = Paint()

        mPaint.isAntiAlias = true //抗锯齿功能
        mPaint.color = Color.RED //设置画笔颜色
        mPaint.strokeWidth = 5f //设置画笔宽度
        mPaint.textSize = 100f //设置字体大小
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawLine(canvas)
        drawPoint(canvas)
    }

    private fun drawLine(canvas: Canvas?) {

        canvas?.drawLine(10f, 10f, 10f, 100f, mPaint)

        val pts = floatArrayOf(10f, 10f, 100f, 100f, 200f, 200f, 400f, 400f)
        canvas?.drawLines(pts, mPaint)
    }

    private fun drawPoint(canvas: Canvas?) {

        mPaint.strokeWidth = 20f
        canvas?.drawPoint(10f, 50f, mPaint)

        mPaint.strokeCap = Paint.Cap.ROUND
        val pts = floatArrayOf(10f, 20f, 100f, 200f, 200f, 300f, 400f, 500f)
        canvas?.drawPoints(pts, mPaint)
    }
}