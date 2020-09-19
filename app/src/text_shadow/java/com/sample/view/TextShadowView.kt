package com.sample.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * @date: 2020/9/13
<p>
 * @author: ice_coffee
<p>
 * @remark:
 */
class TextShadowView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var mPaint: Paint = Paint();

    /**
     * 通过setShadowLayer(float radius, float dx, float dy, @ColorInt int shadowColor)方法实现添加文字阴影
     */
    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.RED
        mPaint.strokeWidth = 5f
        mPaint.textSize = 100f

        mPaint.setShadowLayer(10f, 10f, 10f, Color.GREEN)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawText("Text_Shadow", 100f, 100f, mPaint)
    }
}