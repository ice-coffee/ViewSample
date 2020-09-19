package com.sample.view.weights

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.sample.common.utils.LogUtils


/**
 * @date: 2020/9/19
<p>
 * @author: ice_coffee
<p>
 * @remark:
 */
class LinearGradientView(context: Context?, attributeSet: AttributeSet?) : AppCompatTextView(context!!, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val mLinearGradient = LinearGradient(0f, 0f, measuredWidth.toFloat(), 0f, intArrayOf(Color.RED, Color.GREEN), null, Shader.TileMode.REPEAT)
        paint.shader = mLinearGradient
    }
}