package com.sample.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sample.view.viewdraghelper.ViewDragHelperActivity

/**
 * date: 2020/9/13
 * author: ice_coffee
 * remark:
 */
class ViewHelperActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_helper)
    }

    fun jumpViewDragHelper(view: View) {
        startActivity(Intent(this, ViewDragHelperActivity::class.java))
    }
}