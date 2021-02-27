package com.sample.view.recyclerview

/**
 * date: 2020/12/2
 * @author: ice_coffee
 * remark:
 */
class StudentBean(val id: Int, val name: String, val age: Int, val gender: Int) {
    companion object {
        const val GENDER_GRIL = 0
        const val GENDER_BOY = 1
    }
}