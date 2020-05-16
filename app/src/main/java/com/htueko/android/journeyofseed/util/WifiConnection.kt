package com.htueko.android.journeyofseed.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.lifecycle.LiveData

class WifiConnection(private val context: Context) : LiveData<Boolean>() {

    // to get the wifi manager
    private var wifiManger: WifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    override fun onActive() {
        super.onActive()
        postValue(true)
        context.registerReceiver(wifiReceiver, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
    }

    override fun onInactive() {
        super.onInactive()
        postValue(false)
        context.unregisterReceiver(wifiReceiver)
    }

    private val wifiReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val wifiStateExtra =
                intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
            when (wifiStateExtra) {
                WifiManager.WIFI_STATE_ENABLED -> {
                    postValue(true)
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    postValue(false)
                }
            }
        }

    }

}