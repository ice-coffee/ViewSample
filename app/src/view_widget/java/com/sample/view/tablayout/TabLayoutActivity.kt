package com.sample.view.tablayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.sample.view.R
import kotlinx.android.synthetic.main.activity_tab_layout.*

/**
 *  @author mzp
 *  date : 2020/11/13
 *  desc :
 */
class TabLayoutActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tab_layout)

//        tabLayout1.getTabAt(0)?.text = "0"
//        tabLayout1.getTabAt(1)?.text = "1"
//        tabLayout1.getTabAt(2)?.text = "2"
//        tabLayout1.getTabAt(3)?.text = "3"

        tabLayout1.addTab(tabLayout1.newTab().setText("Tab 1"))
        tabLayout1.addTab(tabLayout1.newTab().setText("Tab 2"))
        tabLayout1.addTab(tabLayout1.newTab().setText("Tab 3"))
    }
}