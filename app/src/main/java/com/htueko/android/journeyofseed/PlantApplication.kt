package com.htueko.android.journeyofseed

import android.app.Application
import com.htueko.android.journeyofseed.util.network.NetworkConnectivityChecker

class PlantApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initNetworkConnectivityChecker()
    }

    private fun initNetworkConnectivityChecker() {
        NetworkConnectivityChecker.init(this.applicationContext)
    }

}