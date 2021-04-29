package com.sample.hilt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sample.hilt.bean.UserBean
import com.sample.hilt.bean.WaterBean
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *  @author mzp
 *  date : 2021/4/28
 *  desc :
 */
@AndroidEntryPoint
class OtherHiltActivity: AppCompatActivity() {

    @Inject
    lateinit var userBean: UserBean

    @Inject
    lateinit var waterBean: WaterBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_hilt)

        Log.e("hilt", userBean.toString())
        Log.e("hilt", waterBean.toString())
    }
}