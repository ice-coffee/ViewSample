package com.sample.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sample.view.appbarLayout.AppbarLayoutActivity
import com.sample.view.recyclerview.RecyclerViewActivity
import com.sample.view.tablayout.TabLayoutActivity
import com.sample.view.viewSwitcher.ViewSwitcherActivity

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

    fun jumpViewSwitcher(view: View) {
        startActivity(Intent(this, ViewSwitcherActivity::class.java))
    }

    fun jumpAppbarLayout(view: View) {
        startActivity(Intent(this, AppbarLayoutActivity::class.java))
    }

    fun jumpTabLayout(view: View) {
        startActivity(Intent(this, TabLayoutActivity::class.java))
    }

    fun jumpRecyclerView(view: View) {
        startActivity(Intent(this, RecyclerViewActivity::class.java))
    }
}