package com.sample.hilt

import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

/**
 *  @author mzp
 *  date : 2021/4/23
 *  desc :
 */
@ActivityScoped
class UserBean @Inject constructor() {
    var name: String = ""
    var age: Int = 0
}