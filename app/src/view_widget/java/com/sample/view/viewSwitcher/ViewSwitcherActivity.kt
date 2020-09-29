package com.sample.view.viewSwitcher

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sample.view.R

/**
 * date: 2020/9/20
 * author: ice_coffee
 * remark:
 */
class ViewSwitcherActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_switcher)

        replaceFragment(TextSwitcherFragment())
    }

    fun replaceTextSwitcher(view: View) {

        replaceFragment(TextSwitcherFragment())
    }

    fun replaceImageSwitcher(view: View) {

        replaceFragment(ImageSwitcherFragment())
    }

    private fun replaceFragment(fragment: Fragment) {

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.llFragmentContent, fragment)
        ft.commitAllowingStateLoss()
    }
}