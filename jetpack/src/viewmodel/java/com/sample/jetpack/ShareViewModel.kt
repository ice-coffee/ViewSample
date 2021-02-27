package com.sample.jetpack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @date: 2021/2/27
 * @author: ice_coffee
 * remark:
 */
class ShareViewModel: ViewModel() {
    val clickLiveData = MutableLiveData<Int>()
}