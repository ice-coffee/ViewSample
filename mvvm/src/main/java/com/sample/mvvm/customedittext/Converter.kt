package com.sample.mvvm.customedittext

import androidx.databinding.InverseMethod

/**
 * @date: 2021/4/18
 * @author: ice_coffee
 * remark:
 */
object Converter {
    @InverseMethod("stringToInt")
    @JvmStatic
    fun intToString(username: String, newValue: Int): String {
        return newValue.toString()
    }

    @JvmStatic
    fun stringToInt(username: String, newValue: String): Int {
        return newValue.toInt()
    }
}