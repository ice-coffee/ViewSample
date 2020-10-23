package com.sample.view.weights

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 *  @author mzp
 *  date : 2020/10/23
 *  desc :
 */
class PathDashPathEffectView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 画笔初始设置
        // 画笔初始设置
        val paint = Paint()
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.isAntiAlias = true

        val rectF = RectF(0f, 0f, 50f, 50f)

        // 方形
        val rectPath = Path()
        rectPath.addRect(rectF, Path.Direction.CW)

        // 圆形 椭圆
        val ovalPath = Path()
        ovalPath.addOval(rectF, Path.Direction.CW)

        // 子弹形状
        val bulletPath = Path()
        bulletPath.lineTo(rectF.centerX(), rectF.top)
        bulletPath.addArc(rectF, -90f, 180f)
        bulletPath.lineTo(rectF.left, rectF.bottom)
        bulletPath.lineTo(rectF.left, rectF.top)

        // 星星形状
        val pathMeasure = PathMeasure(ovalPath, false)
        val length = pathMeasure.length
        val split = length / 5
        val starPos = FloatArray(10)
        val pos = FloatArray(2)
        val tan = FloatArray(2)
        for (i in 0..4) {
            pathMeasure.getPosTan(split * i, pos, tan)
            starPos[i * 2 + 0] = pos[0]
            starPos[i * 2 + 1] = pos[1]
        }
        val starPath = Path()
        starPath.moveTo(starPos[0], starPos[1])
        starPath.lineTo(starPos[4], starPos[5])
        starPath.lineTo(starPos[8], starPos[9])
        starPath.lineTo(starPos[2], starPos[3])
        starPath.lineTo(starPos[6], starPos[7])
        starPath.lineTo(starPos[0], starPos[1])
        val matrix = Matrix()
        matrix.postRotate(-90f, rectF.centerX(), rectF.centerY())
        starPath.transform(matrix)

        canvas!!.translate(360f, 100f)
        // 绘制分割线 - 方形
        canvas.translate(0f, 100f)
        paint.pathEffect = PathDashPathEffect(rectPath, rectF.width() * 1.5f, 0f, PathDashPathEffect.Style.TRANSLATE)
        canvas.drawLine(0f, 0f, 1200f, 0f, paint)

        // 绘制分割线 - 圆形
        canvas.translate(0f, 100f)
        paint.pathEffect = PathDashPathEffect(ovalPath, rectF.width() * 1.5f, 0f, PathDashPathEffect.Style.TRANSLATE)
        canvas.drawLine(0f, 0f, 1200f, 0f, paint)

        // 绘制分割线 - 子弹型
        canvas.translate(0f, 100f)
        paint.pathEffect = PathDashPathEffect(bulletPath, rectF.width() * 1.5f, 0f, PathDashPathEffect.Style.TRANSLATE)
        canvas.drawLine(0f, 0f, 1200f, 0f, paint)

        // 绘制分割线 - 星型
        canvas.translate(0f, 100f)
        paint.pathEffect = PathDashPathEffect(starPath, rectF.width() * 1.5f, 0f, PathDashPathEffect.Style.TRANSLATE)
        canvas.drawLine(0f, 0f, 1200f, 0f, paint)
    }
}