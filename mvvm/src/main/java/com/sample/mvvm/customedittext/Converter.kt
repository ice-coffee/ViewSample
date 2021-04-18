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
    fun intToString(newValue: Int): String {
        return newValue.toString()
    }

    @JvmStatic
    fun stringToInt(newValue: String): Int {
        return newValue.toInt()
    }
}