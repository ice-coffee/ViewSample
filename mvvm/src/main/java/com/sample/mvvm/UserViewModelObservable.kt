package com.sample.mvvm

import androidx.databinding.Bindable
import kotlin.random.Random

/**
 * @date: 2021/4/17
 * @author: ice_coffee
 * remark:
 */
class UserViewModelObservable {

    //Bindable 注释会在 BR 类文件中生成一个条目
    var userName: String = ""
        set(value) {
            field = if (value.length > 1) value.substring(0, 1) else value
        }

    val random = Random(20)

    fun updateUserNameValue() {
        userName = random.nextInt().toString()
    }
}