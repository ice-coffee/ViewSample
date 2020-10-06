package com.sample.view.appbarLayout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sample.view.R

/**
 *  @author mzp
 *  date : 2020/9/29
 *  desc :
 */
class AppbarLayoutActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appbarlayout)

        replaceFragment(SampleOneFragment())
    }

    fun replaceSampleOne(view: View) {

        replaceFragment(SampleOneFragment())
    }

    fun replaceSampleTwo(view: View) {

        replaceFragment(SampleTwoFragment())
    }

    fun replaceSampleThree(view: View) {

        replaceFragment(SampleThreeFragment())
    }

    fun replaceSampleFour(view: View) {

        replaceFragment(SampleFourFragment())
    }

    fun replaceSampleFive(view: View) {

        replaceFragment(SampleFiveFragment())
    }

    private fun replaceFragment(fragment: Fragment) {

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.llFragmentContent, fragment)
        ft.commitAllowingStateLoss()
    }
}