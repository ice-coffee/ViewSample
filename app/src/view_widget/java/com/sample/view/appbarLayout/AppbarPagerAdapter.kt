package com.sample.view.appbarLayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *  @author mzp
 *  date : 2020/11/13
 *  desc :
 */
class AppbarPagerAdapter(fragmentManager: FragmentManager, private val pagerFragments: List<Fragment>): FragmentPagerAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return pagerFragments[position]
    }

    override fun getCount(): Int {
        return pagerFragments.size
    }
}