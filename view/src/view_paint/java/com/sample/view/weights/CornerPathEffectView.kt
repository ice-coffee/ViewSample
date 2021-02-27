package com.sample.view.weights

import android.content.Context
import android.graphics.Canvas
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * @date: 2020/9/12
<p>
 * @author: ice_coffee
<p>
 * @remark: 本示例介绍了如下功能
 * 1. Path 的基础用法
 * 2. Paint.style 的用法 (使用 Paint.Style.FILL 效果会很飘)
 * 3. Paint.strokeCap 的用法
 * 4. Paint.strokeJoin 的用法 (测试无效)
 *
 * 注意: 使用 Path 实现手绘功能时线段不平滑, 可以使用 CornerPathEffect 优化.
 */
class CornerPathEffectView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val mPaint = Paint()
    private var mPath = Path()

    private var viewHeight = 0

    init {
        mPaint.strokeWidth = 20f
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeJoin = Paint.Join.ROUND

        //绘制 Path 更加平滑
        mPaint.pathEffect = CornerPathEffect(200f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 绘制原始路径
        canvas!!.save()
        canvas.drawPath(mPath, mPaint)
        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mPath.reset()
                mPath.moveTo(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> mPath.lineTo(event.x, event.y)
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
            }
        }
        postInvalidate()
        return true
    }
}