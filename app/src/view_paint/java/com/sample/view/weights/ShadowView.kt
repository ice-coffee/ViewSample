package com.sample.view.weights

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.sample.common.utils.dp2px

/**
 *  @author mzp
 *  date : 2020/9/18
 *  desc :
 */
class ShadowView: View {

    lateinit var mPaint: Paint

    constructor(context: Context):super(context){ initView() }
    constructor(context: Context, attributes: AttributeSet?):super(context, attributes){ initView() }
    constructor(context: Context, attributes: AttributeSet?, defStyleAttr: Int):super(context, attributes, defStyleAttr){ initView() }

    private fun initView() {

        mPaint = Paint()

        mPaint.isAntiAlias = true //抗锯齿功能
        mPaint.color = Color.RED //设置画笔颜色
        mPaint.strokeWidth = 5f //设置画笔宽度
        mPaint.textSize = dp2px(32f).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawTextShadow(canvas!!)
    }

    /**
     * 绘制阴影层
     * setShadowLayer
     * @param radius 阴影半径
     * @param dx x轴偏移量
     * @param dy y轴偏移量
     * @param color 阴影颜色
     * 可用于创建文字下面的模糊阴影。对其他绘图操作的支持仅限于软件渲染管道。
     */
    private fun drawTextShadow(canvas: Canvas) {

        mPaint.setShadowLayer(10f, 5f, 5f, Color.GREEN) //设置阴影
        canvas.drawText("Paint_Style", 110f, 110f, mPaint)
    }
}