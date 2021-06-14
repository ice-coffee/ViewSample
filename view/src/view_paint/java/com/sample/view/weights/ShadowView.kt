package com.sample.view.weights

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.sample.common.utils.dp2px
import com.sample.common.views.BaseView

/**
 * @date: 2020/9/13
 * @author: ice_coffee
 * @remark:
 * radius       : 阴影半径
 * dx           : x轴偏移量
 * dy           : y轴偏移量
 * shadowColor  : 阴影颜色
 * 通过Paint.setShadowLayer(float radius, float dx, float dy, @ColorInt int shadowColor)方法添加阴影图层,
 * 当使用Canvas绘制各种图形时就会带有阴影, 可用于创建文字下面的模糊阴影, 对其他绘图操作的支持仅限于软件渲染管道。
 */
class ShadowView(context: Context?, attrs: AttributeSet?) : BaseView(context, attrs) {

    var mPaint: Paint = Paint()

    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.RED
        mPaint.strokeWidth = 5f
        mPaint.textSize = dp2px(32).toFloat()

        mPaint.setShadowLayer(10f, 10f, 10f, Color.GREEN)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), dp2px(200))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawText("Text_Shadow", 0f, 100f, mPaint)
    }
}