package com.sample.view.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * date: 2020/12/12
 * @author: ice_coffee
 * remark:
 */
class SquareLinearLayout(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}