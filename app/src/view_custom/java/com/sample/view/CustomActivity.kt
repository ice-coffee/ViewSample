package com.sample.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_custom.*

/**
 *  @author mzp
 *  date : 2020/11/4
 *  desc :
 */
public class CustomActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_custom)

        seekBarView.setVideoDuration(120 * 1000)
        seekBarView.addRangeChangeListener { startTime, maxValue -> }
    }
}