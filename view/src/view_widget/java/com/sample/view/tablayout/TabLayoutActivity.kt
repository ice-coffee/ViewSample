package com.sample.view.tablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sample.view.MyFragmentPagerAdapter
import com.sample.view.R
import kotlinx.android.synthetic.main.activity_tab_layout.*

/**
 *  @author mzp
 *  date : 2020/11/13
 *  desc :
 */
class TabLayoutActivity: AppCompatActivity() {

    private val pageTitles = arrayListOf("首页", "消息", "我")
    private val tabIcons = arrayListOf(R.drawable.selector_ic_home, R.drawable.selector_ic_infor, R.drawable.selector_ic_mine)
    private val pagerFragmengs = arrayListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tab_layout)

        initFragmentList()

        viewpager.adapter = MyFragmentPagerAdapter(supportFragmentManager, pageTitles, pagerFragmengs)
        tabLayout.setupWithViewPager(viewpager)

        for (i in 0 until pageTitles.size) {
            val view = LayoutInflater.from(this).inflate(R.layout.item_tab_layout, null)
            val ivTabIcon = view.findViewById<ImageView>(R.id.ivTabIcon)
            val tvTabContent = view.findViewById<TextView>(R.id.tvTabContent)
            ivTabIcon.setImageResource(tabIcons[i])
            tvTabContent.text = pageTitles[i]
            tabLayout.getTabAt(i)?.customView = view
        }
    }

    private fun initFragmentList() {
        for (i in 0 until pageTitles.size) {
            pagerFragmengs.add(TabLayoutFragment.getInstance(pageTitles[i]))
        }
    }
}