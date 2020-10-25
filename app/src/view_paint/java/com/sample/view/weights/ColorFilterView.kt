package com.sample.view.weights

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.sample.view.R

/**
 * date: 2020/10/24
 * author: ice_coffee
 * remark:
 */
class ColorFilterView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val colorMatrixArray = floatArrayOf(
            -1f, 0f, 0f, 0f, 255f,
            0f, -1f, 0f, 0f, 255f,
            0f, 0f, -1f, 0f, 255f,
            0f, 0f, 0f, 1f, 0f)
    private val colorMatrixArray1 = floatArrayOf(1f, 0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint()

        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.colormatrix)
        val srcRect = Rect(0, 0, bitmap.width, bitmap.height)
        val dstRect = Rect(0, 0, bitmap.width, bitmap.height)

        canvas!!.drawBitmap(bitmap, srcRect, dstRect, paint)

        canvas.translate(0f, bitmap.height.toFloat() + 10)
        paint.colorFilter = ColorMatrixColorFilter(colorMatrixArray)
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint)

        canvas.translate(0f, bitmap.height.toFloat())
        paint.colorFilter = ColorMatrixColorFilter(colorMatrixArray1)
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint)
    }
}