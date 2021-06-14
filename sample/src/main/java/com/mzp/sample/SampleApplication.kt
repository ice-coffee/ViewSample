package com.mzp.sample

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.sample.jetpack.lifecycle.CustomObserver
import dagger.hilt.android.HiltAndroidApp

/**
 * @date: 2021/2/28
 * @author: ice_coffee
 * remark:
 */
@HiltAndroidApp
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(CustomObserver("app"))
    }
}