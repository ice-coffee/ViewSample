package com.sample.view.appbarLayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sample.view.MyFragmentPagerAdapter
import com.sample.view.R
import kotlinx.android.synthetic.main.activity_appbarlayout.*

/**
 *  @author mzp
 *  date : 2020/9/29
 *  desc :
 */
class AppbarLayoutActivity : AppCompatActivity() {

    private val pageTitles = arrayListOf<String>()
    private val pagerFragmengs = arrayListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appbarlayout)

        initFragmentList()

        appbarViewPager.adapter = MyFragmentPagerAdapter(supportFragmentManager, pageTitles, pagerFragmengs)
        appbarTab.setupWithViewPager(appbarViewPager)
    }

    private fun initFragmentList() {
        pagerFragmengs.add(SampleSixFragment())
        pagerFragmengs.add(SampleOneFragment())
        pagerFragmengs.add(SampleTwoFragment())
        pagerFragmengs.add(SampleThreeFragment())
        pagerFragmengs.add(SampleFourFragment())
        pagerFragmengs.add(SampleFiveFragment())

        for (i in 0..pagerFragmengs.size) {
            pageTitles.add(String.format(getString(R.string.appbar_sample_format), i))
        }
    }
}