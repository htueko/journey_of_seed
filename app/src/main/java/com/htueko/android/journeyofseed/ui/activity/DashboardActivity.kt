package com.htueko.android.journeyofseed.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.htueko.android.journeyofseed.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        fab_dashboard.setOnClickListener {
            startActivity(Intent(this, InsertActivity::class.java))
        }

    }

}
