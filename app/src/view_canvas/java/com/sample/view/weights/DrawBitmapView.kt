package com.sample.view.weights

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.sample.view.R

/**
 *  @author mzp
 *  date : 2020/10/26
 *  desc :
 */
class DrawBitmapView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.right)
    private val srcRect = Rect(0, 0, 0, bitmap.height)
    private val dstRect = Rect(0, 0, 0, bitmap.height)

    private var currentTimes = 0
    private val drawTimes = 10

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        runPost()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        srcRect.right = bitmap.width * currentTimes / drawTimes
        dstRect.right = bitmap.width * currentTimes / drawTimes
        canvas?.translate(0f, 20f)
        canvas?.drawBitmap(bitmap, srcRect, dstRect, paint)
    }

    private fun runPost() {
        handler.postDelayed({
            currentTimes++
            invalidate()
            runPost()
        }, 200)
    }
}