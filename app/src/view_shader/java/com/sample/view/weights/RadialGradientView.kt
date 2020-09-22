package com.sample.view.weights

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AccelerateInterpolator
import com.sample.common.views.BaseView

/**
 * date: 2020/9/20
 * author: ice_coffee
 * remark: TODO-MZP 本实例仅用于演示 RadialGradient 的用法, 在android material design 设计规范中按钮水波纹使用 material.ripple实现
 */
class RadialGradientView(context: Context?, attributeSet: AttributeSet?) : BaseView(context, attributeSet) {

    private var mX = 0f
    private var mY = 0f
    private var mAnimator: ObjectAnimator? = null
    private var mCurRadius = 0f
    private val mPaint: Paint = Paint()

    companion object {
        private const val DEFAULT_RADIUS = 50
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_UP) {
            mX = event.x
            mY = event.y

            mAnimator?.isRunning ?: mAnimator?.cancel()
            mAnimator?.start()
        }
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        //自定义属性动画
        mAnimator = ObjectAnimator.ofInt(this, "radius", DEFAULT_RADIUS, w)
        mAnimator?.interpolator = AccelerateInterpolator()
        mAnimator?.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                setRadius(0)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(mX, mY, mCurRadius, mPaint)
    }

    //注意这里的方法名必须是setRadius(自定义属性动画)
    fun setRadius(radius: Int) {
        mCurRadius = radius.toFloat()
        if (mCurRadius > 0) {
            mPaint.shader = RadialGradient(mX, mY, mCurRadius, 0x00FFFFFF, -0xa70554, Shader.TileMode.CLAMP)
        }
        invalidate()
    }
}