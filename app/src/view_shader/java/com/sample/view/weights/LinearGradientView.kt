package com.sample.view.weights

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


/**
 * date: 2020/9/19
 * author: ice_coffee
 * remark:
 */
class LinearGradientView(context: Context?, attributeSet: AttributeSet?) : AppCompatTextView(context!!, attributeSet) {

    var mLinearGradient: LinearGradient? = null
    private val mMatrix = Matrix()
    private var mX = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mLinearGradient = LinearGradient(0f, 0f, measuredWidth.toFloat(), 0f, intArrayOf(Color.GREEN, Color.RED, Color.GREEN), null, Shader.TileMode.REPEAT)
        paint.shader = mLinearGradient

        initAnimator(w)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mMatrix.reset()
        mMatrix.preTranslate(mX.toFloat(), 0f)
        mLinearGradient!!.setLocalMatrix(mMatrix)
    }

    private fun initAnimator(width: Int) {
        val animator = ValueAnimator.ofInt(0, width * 2) //我们设置value的值为0-getMeasureWidth的3 倍
        animator.addUpdateListener { animation ->
            mX = animation.animatedValue as Int
            postInvalidate()
        }
        animator.repeatMode = ValueAnimator.RESTART //重新播放
        animator.repeatCount = ValueAnimator.INFINITE //无限循环
        animator.duration = 2000
        animator.start()
    }
}