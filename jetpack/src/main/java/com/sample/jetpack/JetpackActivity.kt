package com.sample.jetpack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sample.jetpack.databinding.DataBindingActivity
import com.sample.jetpack.lifecycle.LifecycleActivity
import com.sample.jetpack.livedata.LiveDataActivity
import com.sample.jetpack.viewmodel.ViewModelActivity

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

    fun jumpViewModel(view: View) {
        startActivity(Intent(this, ViewModelActivity::class.java))
    }

    fun jumpLiveData(view: View) {
        startActivity(Intent(this, LiveDataActivity::class.java))
    }

    fun jumpLifecycle(view: View) {
        startActivity(Intent(this, LifecycleActivity::class.java))
    }

    fun jumpDataBinding(view: View) {
        startActivity(Intent(this, DataBindingActivity::class.java))
    }
}