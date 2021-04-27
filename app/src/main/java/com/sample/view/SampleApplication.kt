package com.sample.view

import androidx.lifecycle.ProcessLifecycleOwner
import com.sample.common.BaseApplication
import com.sample.jetpack.lifecycle.CustomObserver
import dagger.hilt.android.HiltAndroidApp

/**
 * @date: 2021/2/28
 * @author: ice_coffee
 * remark:
 */
@HiltAndroidApp
class SampleApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(CustomObserver("app"))
    }
}