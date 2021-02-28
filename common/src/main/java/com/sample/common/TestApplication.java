package com.sample.common;

import android.app.Application;

/**
 * @date: 2021/2/28
 * @author: ice_coffee
 * remark:
 */
class TestApplication extends Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Application getInstance() {
        return instance;
    }
}
