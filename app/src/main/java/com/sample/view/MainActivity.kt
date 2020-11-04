package com.sample.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sample.common.utils.LogUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @date: 2020/9/6
<p>
 * @author: ice_coffee
<p>
 * @remark:
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBarView.setVideoDuration(120 * 1000)
        seekBarView.addRangeChangeListener { startTime, maxValue -> LogUtils.Companion.e("$startTime, $maxValue") }
    }

    fun jumpWidget(view: View) {
        startActivity(Intent(this, WidgetActivity::class.java))
    }

    fun jumpHelper(view: View) {
        startActivity(Intent(this, ViewHelperActivity::class.java))
    }

    fun jumpRoundView(view: View) {
        startActivity(Intent(this, RoundViewActivity::class.java))
    }

    fun jumpViewPaint(view: View) {
        startActivity(Intent(this, PaintActivity::class.java))
    }

    fun jumpViewCanvas(view: View) {
        startActivity(Intent(this, CanvasActivity::class.java))
    }

    fun jumpTextShader(view: View) {
        startActivity(Intent(this, ShaderActivity::class.java))
    }

    fun jumpGestureDetector(view: View) {
        startActivity(Intent(this, GestureActivity::class.java))
    }
}