package com.sample.hilt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 *  @author mzp
 *  date : 2021/4/27
 *  desc :
 */
@AndroidEntryPoint
class BHiltActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_hilt)

        Log.e("hilt test", HiltModule.toString())
    }
}