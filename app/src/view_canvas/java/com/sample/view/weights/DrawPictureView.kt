package com.sample.view.weights

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.sample.common.utils.LogUtils
import com.sample.view.R

/**
 *  @author mzp
 *  date : 2020/10/26
 *  desc : drawPicture 对于多次重复绘制某图形能达到节约资源的作用.
 */
class DrawPictureView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val picture = Picture()
    private val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.maskfilter)
    private val srcRect = Rect(0, 0, bitmap.width, bitmap.height)
    private val dstRect = Rect(0, 0, bitmap.width, bitmap.height)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val pictureCanvas = picture.beginRecording(0, 0)
        pictureCanvas.drawBitmap(bitmap, srcRect, dstRect, paint)
        picture.endRecording()


        val startTime = System.currentTimeMillis()
        for (i in 0..500) {
            canvas!!.translate(0f, 2f)
//            canvas.drawBitmap(bitmap, srcRect, dstRect, paint)
            canvas.drawPicture(picture)
        }
        val endTime = System.currentTimeMillis()
        LogUtils.e(endTime - startTime)
    }
}