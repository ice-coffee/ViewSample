package com.sample.jetpack.livedata

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sample.jetpack.R

/**
 * @date: 2021/2/28
 * @author: ice_coffee
 * remark:
 */
class LiveDataActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)

        val networkLiveData = NetworkLiveData(this)
        networkLiveData.observe(this, Observer {
            value ->
            if (value == 1){
                Log.e("livedata", "is work")
            } else{
                Log.e("livedata", "is not work")
            }
        })
    }
}