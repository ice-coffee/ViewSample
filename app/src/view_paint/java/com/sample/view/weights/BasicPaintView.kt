package com.sample.view.weights

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import com.sample.common.utils.dp2px
import com.sample.common.views.BaseView

/**
 * @date: 2020/9/12
<p>
 * @author: ice_coffee
<p>
 * @remark:
 */
class BasicPaintView(context: Context?, attrs: AttributeSet?) : BaseView(context, attrs) {

    lateinit var mPaint: Paint

    private fun initView() {

        mPaint = Paint()

        mPaint.isAntiAlias = true //抗锯齿功能
        mPaint.color = Color.RED //设置画笔颜色
        mPaint.strokeWidth = 5f //设置画笔宽度
        mPaint.textSize = dp2px(32).toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), dp2px(200))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paintStyle(canvas!!)
//        paintCap(canvas!!)
//        paintJoin(canvas!!)
    }

    /**
     * Paint.Style
     * 填充模式
     * Paint.Style.FILL             : 填充内部
     * Paint.Style.STROKE           : 填充描边
     * Paint.Style.FILL_AND_STROKE  : 填充内部和描边
     */
    private fun paintStyle(canvas: Canvas) {

        //default
        canvas.drawCircle(100f, 100f, 100f, mPaint)

        //Paint.Style.FILL
        canvas.translate(250f, 0f)
        mPaint.style = Paint.Style.FILL
        canvas.drawCircle(100f, 100f, 100f, mPaint)

        //Paint.Style.STROKE
        canvas.translate(250f, 0f)
        mPaint.style = Paint.Style.STROKE
        canvas.drawCircle(100f, 100f, 100f, mPaint)

        //Paint.Style.FILL_AND_STROKE
        canvas.translate(250f, 0f)
        mPaint.style = Paint.Style.FILL_AND_STROKE
        canvas.drawCircle(100f, 100f, 100f, mPaint)
    }

    /**
     * Paint.Cap
     * 线段开始和结束样式
     * Paint.Cap.BUTT             : 默认不添加样式
     * Paint.Cap.ROUND            : 半圆样式
     * Paint.Cap.SQUARE           : 长方形样式
     */
    private fun paintCap(canvas: Canvas) {

        mPaint.strokeWidth = dp2px(30).toFloat()

        mPaint.strokeCap = Paint.Cap.BUTT
        canvas.drawLine(50f, 10f, 200f, 10f, mPaint)

        canvas.translate(0f, 200f)
        mPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawLine(50f, 10f, 200f, 10f, mPaint)

        canvas.translate(0f, 200f)
        mPaint.strokeCap = Paint.Cap.SQUARE
        canvas.drawLine(50f, 10f, 200f, 10f, mPaint)
    }

    /**
     * Paint.Join (测试无效)
     * 线段连接方式(拐角类型)
     * Paint.Join.MITER         : 尖角 (默认模式)
     * Paint.Join.BEVEL	        : 平角
     * Paint.Join.ROUND	        : 圆角
     */
    private fun paintJoin(canvas: Canvas) {

        val path = Path()
        path.moveTo(100f, 100f)
        path.lineTo(450f, 100f)
        path.lineTo(100f, 300f)
        mPaint.strokeJoin = Paint.Join.MITER
        canvas.drawPath(path, mPaint)

        path.moveTo(100f, 400f)
        path.lineTo(450f, 400f)
        path.lineTo(100f, 600f)
        mPaint.strokeJoin = Paint.Join.BEVEL
        canvas.drawPath(path, mPaint)

        path.moveTo(100f, 700f)
        path.lineTo(450f, 700f)
        path.lineTo(100f, 900f)
        mPaint.strokeJoin = Paint.Join.ROUND
        canvas.drawPath(path, mPaint)
    }
}