package com.htueko.android.journeyofseed.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.htueko.android.journeyofseed.R
import com.htueko.android.journeyofseed.util.WifiConnection
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()

        val wifiConnection = WifiConnection(applicationContext)
        wifiConnection.observe(this, Observer { isConnected ->
            if (isConnected) {

                Toast.makeText(this, "wifi is connected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "wifi isn't connected", Toast.LENGTH_SHORT).show()
                lottie_plant_splash.visibility = View.GONE
                lottie_no_connection_splash.visibility = View.VISIBLE
                tv_no_connection_splash.visibility = View.VISIBLE
                switch_splash.visibility = View.VISIBLE
            }
        })

    }


}
