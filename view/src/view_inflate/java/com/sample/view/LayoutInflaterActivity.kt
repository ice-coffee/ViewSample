package com.sample.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_layout_inflate.*

/**
 * date: 2020/11/8
 * author: ice_coffee
 * remark:
 */
public class LayoutInflaterActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_inflate)

        val view = LayoutInflater.from(this).inflate(R.layout.view_add, null)
        llIRoot.addView(view)
    }
}