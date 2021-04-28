package com.sample.hilt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *  @author mzp
 *  date : 2021/4/27
 *  desc :
 */
@AndroidEntryPoint
class AHiltActivity: AppCompatActivity() {

    @Inject
    lateinit var userBean: UserBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_hilt)

        userBean.name = "李杨"
        userBean.age = 3

        Log.e("hilt", "${userBean.name} 今年 ${userBean.age} 了")
    }

    fun jumpBHilt(view: View) {
        startActivity(Intent(this, BHiltActivity::class.java))
    }
}