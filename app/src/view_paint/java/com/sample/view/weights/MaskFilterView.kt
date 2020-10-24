package com.sample.view.weights

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.helper.widget.Layer
import com.sample.view.R

/**
 * date: 2020/10/24
 * author: ice_coffee
 * remark:
 */
class MaskFilterView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var viewHeight = 0
    private var viewWidth = 0
    private var drawWidth = 0
    private val padding = 100

    init {
        setLayerType(Layer.LAYER_TYPE_SOFTWARE, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewHeight = h
        viewWidth = w
        drawWidth = viewWidth / 2 - padding * 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawColor(Color.BLACK)

        val drawBitmap = BitmapFactory.decodeResource(resources, R.mipmap.icon_point)
        val srcRect = Rect(0, 0, drawBitmap.width, drawBitmap.height)
        val dstRect = Rect(0, 0, drawWidth, drawWidth)

        val paint = Paint()
        paint.color = Color.RED
        canvas.save()
        canvas.translate(padding.toFloat(), padding.toFloat())
        paint.maskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL)
        canvas.drawBitmap(drawBitmap, srcRect, dstRect, paint)
        canvas.restore()

        canvas.save()
        canvas.translate(padding.toFloat() + viewWidth / 2, padding.toFloat())
        paint.maskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.SOLID)
        canvas.drawBitmap(drawBitmap, srcRect, dstRect, paint)
        canvas.restore()

        canvas.save()
        canvas.translate(padding.toFloat(), padding.toFloat() * 2 + drawWidth)
        paint.maskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.OUTER)
        canvas.drawBitmap(drawBitmap, srcRect, dstRect, paint)
        canvas.restore()

        canvas.save()
        canvas.translate(padding.toFloat() + viewWidth / 2, padding.toFloat() * 2 + drawWidth)
        paint.maskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.INNER)
        canvas.drawBitmap(drawBitmap, srcRect, dstRect, paint)
        canvas.restore()
    }
}