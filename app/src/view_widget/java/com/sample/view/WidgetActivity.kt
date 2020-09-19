package com.sample.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sample.view.ViewSwitcher.ImageSwitcherActivity
import com.sample.view.ViewSwitcher.TextSwitcherActivity

/**
 * @date: 2020/9/19
<p>
 * @author: ice_coffee
<p>
 * @remark:
 */
class WidgetActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget)
    }

    fun jumpTextSwitcher(view: View) {
        startActivity(Intent(this, TextSwitcherActivity::class.java))
    }

    fun jumpImageSwitcher(view: View) {
        startActivity(Intent(this, ImageSwitcherActivity::class.java))
    }
}