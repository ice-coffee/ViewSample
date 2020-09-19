package com.sample.view

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sample.common.utils.LogUtils
import com.sample.view.weights.LinearGradientFontSpan
import kotlinx.android.synthetic.main.activity_shader.*


/**
 * @date: 2020/9/13
<p>
 * @author: ice_coffee
<p>
 * @remark:
 */
class ShaderActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shader)
    }

    override fun onResume() {
        super.onResume()

        LogUtils.e(tvLeftRight.measuredWidth)

        tvLeftRight.post { tvLeftRight.setText(getGradientSpan(tvLeftRight.width, tvLeftRight.getText().toString())) }
        tvTopBottom.post { tvTopBottom.setText(getGradientSpan(tvTopBottom.width, tvTopBottom.getText().toString())) }
    }

    /**
     * 动态设置TextView文字的横向或纵向渐变色
     */
    fun getGradientSpan(width: Int, string: String?): SpannableStringBuilder? {
        val spannableStringBuilder = SpannableStringBuilder(string)
        val span = LinearGradientFontSpan(string, width)
        spannableStringBuilder.setSpan(span, 0, spannableStringBuilder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 若有需要可以在这里用SpanString系列的其他类，给文本添加下划线、超链接、删除线...等等效果
        return spannableStringBuilder
    }
}