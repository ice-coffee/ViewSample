package com.sample.jetpack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * @date: 2021/2/27
 * @author: ice_coffee
 * remark:
 */
class ShareViewModel(app: Application): AndroidViewModel(app) {
    val clickLiveData = MutableLiveData<Int>(1)
}