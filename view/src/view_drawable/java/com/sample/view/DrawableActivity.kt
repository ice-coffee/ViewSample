package com.sample.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sample.view.ripple.RippleFragment
import kotlinx.android.synthetic.main.activity_drawable.*

/**
 * date: 2020/11/14
 * author: ice_coffee
 * remark:
 */
public class DrawableActivity: AppCompatActivity() {

    private val pageTitles = arrayListOf("ripple")
    private val pagerFragmengs = arrayListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_drawable)

        initFragmentList()

        drawableViewPager.adapter = MyFragmentPagerAdapter(supportFragmentManager, pageTitles, pagerFragmengs)
        drawableTabLayout.setupWithViewPager(drawableViewPager)
    }

    private fun initFragmentList() {
        pagerFragmengs.add(RippleFragment())
    }
}