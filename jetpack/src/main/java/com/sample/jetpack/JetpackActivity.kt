package com.sample.jetpack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sample.view.R

/**
 * @date: 2021/2/27
 * @author: ice_coffee
 * remark:
 */
class JetpackActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)
    }

    public fun jumpViewModel(view: View) {
        startActivity(Intent(this, ViewModelActivity::class.java))
    }

    public fun jumpLiveData(view: View) {
        startActivity(Intent(this, ViewModelActivity::class.java))
    }
}