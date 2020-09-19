package com.sample.common.utils

import com.blankj.utilcode.util.SizeUtils

/**
 * @date: 2020/9/6
<p>
 * @author: ice_coffee
<p>
 * @remark:
 */
fun dp2px(dpValue: Int): Int {
    return SizeUtils.dp2px(dpValue.toFloat())
}