package com.sample.common

import android.app.Application

/**
 * @date: 2021/2/28
 * @author: ice_coffee
 * remark:
 */
open class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: Application? = null
    }
}