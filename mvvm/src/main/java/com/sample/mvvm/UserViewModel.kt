package com.sample.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @date: 2021/4/17
 * @author: ice_coffee
 * remark:
 */
class UserViewModel : ViewModel() {
    var userName = MutableLiveData<String>()

    fun updateUserNameValue() {
        userName.value = System.currentTimeMillis().toString()
    }
}