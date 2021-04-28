package com.sample.hilt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sample.hilt.bean.WaterBean
import com.sample.hilt.bean.CapBean
import com.sample.hilt.bean.UserBean
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *  @author mzp
 *  date : 2021/4/27
 *  desc :
 */
@AndroidEntryPoint
class HiltFragment : Fragment() {
    @Inject
    lateinit var userBean: UserBean
    @Inject
    lateinit var waterBean: WaterBean
    @Inject
    lateinit var schoolBean: CapBean

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.e("hilt test", userBean.toString())
        Log.e("hilt test", waterBean.toString())
        Log.e("hilt test", "${schoolBean}, ${schoolBean.waterBean}")
    }
}