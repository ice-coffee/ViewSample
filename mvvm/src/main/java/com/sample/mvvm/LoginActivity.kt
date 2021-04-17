package com.sample.mvvm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sample.mvvm.databinding.ActivityLoginBinding

/**
 * @date: 2021/4/17
 * @author: ice_coffee
 * remark:
 */
class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)

        val userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.lifecycleOwner = this

        binding.userViewModel = userViewModel
        userViewModel.userName.observe(this, Observer {
            value -> Log.e("livedata_observer", value)
        })

        val userObservable = UserObservable()
        binding.userObservable = userObservable
    }
}