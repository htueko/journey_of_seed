package com.htueko.android.journeyofseed.ui.activity

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.htueko.android.journeyofseed.R
import com.htueko.android.journeyofseed.util.dismiss
import com.htueko.android.journeyofseed.util.network.NetworkConnectivityChecker
import com.htueko.android.journeyofseed.util.show
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        wifiManager = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        setInternetConnectivityObserver()

    }

    override fun onResume() {
        super.onResume()
        NetworkConnectivityChecker.checkForConnection()
    }

    private fun setInternetConnectivityObserver() {
        NetworkConnectivityChecker.observe(this, liveDataObserver)
    }

    private val liveDataObserver: Observer<Boolean> = Observer { isConnected ->
        if (!isConnected) {
            noConnectionHideViews()
        } else {
            hasConnectionShowViews()
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

    private fun noConnectionHideViews() {
        lottie_no_connection_splash.show()
        tv_no_connection_splash.show()
        //switch_splash.show()
        lottie_plant_splash.dismiss()
    }

    private fun hasConnectionShowViews() {
        lottie_no_connection_splash.dismiss()
        tv_no_connection_splash.dismiss()
        //switch_splash.dismiss()
        lottie_plant_splash.show()
    }

}
