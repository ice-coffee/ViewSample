package com.sample.jetpack.livedata

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData

/**
 * @date: 2021/2/28
 * @author: ice_coffee
 * remark:
 */
class NetworkLiveData(private val context: Context) : LiveData<Int>() {

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val networkInfo = connectivityManager!!.activeNetworkInfo

            value = if (networkInfo != null && networkInfo.isAvailable) 1 else 0
        }
    }

    override fun onInactive() {
        super.onInactive()

        context.unregisterReceiver(broadcastReceiver)
    }

    override fun onActive() {
        super.onActive()

        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(broadcastReceiver, intentFilter)
    }

    companion object {
        private lateinit var sInstance: NetworkLiveData

        @MainThread
        fun get(context: Context): NetworkLiveData {
            sInstance = if (::sInstance.isInitialized) sInstance else NetworkLiveData(context.applicationContext)
            return sInstance
        }
    }
}