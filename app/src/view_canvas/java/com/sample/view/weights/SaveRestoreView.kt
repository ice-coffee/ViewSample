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
class SaveRestoreView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.maskfilter)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawBitmap(bitmap, 0f, 0f, paint)

        canvas.saveLayer(RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat()), paint)
        paint.color = Color.RED
        canvas.drawRect(RectF(0f, 0f, bitmap.width.toFloat() / 2, bitmap.height.toFloat() / 2), paint)
        canvas.restore()
    }
}