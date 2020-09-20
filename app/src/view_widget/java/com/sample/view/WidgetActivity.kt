package com.sample.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sample.view.view_switcher.ViewSwitcherActivity

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
        startActivity(Intent(this, ViewSwitcherActivity::class.java))
    }
}