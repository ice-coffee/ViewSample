package com.sample.view.weights

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.sample.common.utils.dp2px

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
        mPaint.textSize = dp2px(32f).toFloat()

        mPaint.setShadowLayer(10f, 10f, 10f, Color.GREEN)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawText("Text_Shadow", 0f, 100f, mPaint)
    }
}