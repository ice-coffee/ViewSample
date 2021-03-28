package com.sample.jetpack.lifecycle

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sample.jetpack.LifecycleFragment
import com.sample.jetpack.R

/**
 * @date: 2021/3/20
 * @author: ice_coffee
 * remark:
 */
class LifecycleActivity : AppCompatActivity() {

    private lateinit var fragment: LifecycleFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
        fragment = LifecycleFragment()

        lifecycle.addObserver(CustomObserver("activity"))
    }

    public fun addFragment(view: View) {
        if (!fragment.isAdded) {
            val ft = supportFragmentManager.beginTransaction()
            ft.add(R.id.fragmentLayout, fragment)
            ft.commit()
        }
    }

    public fun hideFragment(view: View) {
        if (fragment.isAdded) {
            val ft = supportFragmentManager.beginTransaction()
            ft.remove(fragment)
            ft.commit()
        }
    }
}