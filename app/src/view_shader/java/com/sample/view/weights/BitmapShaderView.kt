package com.sample.view.weights

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.sample.common.utils.dp2px
import com.sample.common.views.BaseView
import com.sample.view.R

/**
 * @date: 2020/9/19
<p>
 * @author: ice_coffee
<p>
 * @remark:
 */
class BitmapShaderView(context: Context, attributeSet: AttributeSet): BaseView(context, attributeSet) {

    private val mPaint = Paint()

    init {

        mPaint.isAntiAlias = true //抗锯齿功能
        mPaint.color = Color.RED //设置画笔颜色
        mPaint.strokeWidth = 5f //设置画笔宽度
        mPaint.textSize = dp2px(32).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawByBitmapShader(canvas!!)
    }

    private fun drawByBitmapShader(canvas: Canvas) {

        val bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.naruto)
        val bitmapShader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        mPaint.shader = bitmapShader

        val rect = RectF(0f, 0f, 300f, 300f)

        canvas.drawOval(rect, mPaint)
    }
}