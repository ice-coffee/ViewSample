package com.sample.hilt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sample.hilt.bean.WaterBean
import com.sample.hilt.bean.UserBean
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *  @author mzp
 *  date : 2021/4/27
 *  desc :
 */
@AndroidEntryPoint
class MainHiltActivity: AppCompatActivity() {

    @Inject
    lateinit var firstUserBean: UserBean
    @Inject
    lateinit var secondUserBean: UserBean
    @Inject
    lateinit var waterBean: WaterBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_hilt)

        Log.e("hilt", firstUserBean.toString())
        Log.e("hilt", secondUserBean.toString())
        Log.e("hilt", waterBean.toString())
    }

    fun jumpOtherHilt(view: View) {
        startActivity(Intent(this, OtherHiltActivity::class.java))
    }
}