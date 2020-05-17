package com.htueko.android.journeyofseed.ui.activity

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.htueko.android.journeyofseed.R
import com.htueko.android.journeyofseed.ui.viewmodel.LocationViewModel
import com.htueko.android.journeyofseed.util.COARSE_LOCATION_CODE
import com.htueko.android.journeyofseed.util.PermissionObject
import com.htueko.android.journeyofseed.util.action
import com.htueko.android.journeyofseed.util.snack
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private val locationViewModel by lazy {
        ViewModelProvider(this).get(LocationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        fab_dashboard.setOnClickListener {
            startActivity(Intent(this, InsertActivity::class.java))
        }

        toCheckAndRequestPermissionThenGetTheLocation()

    }

    private fun toCheckAndRequestPermissionThenGetTheLocation() {
        PermissionObject.toCheckAndRequestPermissions(
            activity = this@DashboardActivity,
            permissions = arrayOf(PermissionObject.ACCESS_COARSE_LOCATION_PERMISSION),
            code = COARSE_LOCATION_CODE,
            message = resources.getString(R.string.coarse_location_explanation),
            onSuccess = ::startLocationUpdate
        )
    }

    private fun startLocationUpdate() {
        if (isLocationEnabled()) {
            locationViewModel.getLocationData().observe(this, Observer {

            })
        } else {
            viewgroup_dashboard.snack("Please turn on location") {
                action("OK") {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


}
