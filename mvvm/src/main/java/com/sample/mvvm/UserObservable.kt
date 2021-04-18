package com.sample.mvvm

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.InverseBindingListener
import kotlin.random.Random

/**
 * @date: 2021/4/17
 * @author: ice_coffee
 * remark:
 */
class UserObservable : BaseViewModelObservable() {

    //Bindable 注释会在 BR 类文件中生成一个条目
    @get:Bindable
    var userName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.userName)
        }

    fun updateUserNameValue() {
        userName = "${Random.nextInt(100)}"
    }

    @get:Bindable
    var userAge = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.userAge)
        }

    fun updateUserSchoolValue() {
        userAge = Random.nextInt(100)
    }

    val listener = InverseBindingListener { Log.e("observable", "changed") }
}