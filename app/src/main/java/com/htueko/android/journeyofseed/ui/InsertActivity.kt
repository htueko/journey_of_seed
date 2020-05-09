package com.htueko.android.journeyofseed.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.htueko.android.journeyofseed.R
import com.htueko.android.journeyofseed.data.database.entity.PlantModel
import com.htueko.android.journeyofseed.ui.viewmodel.PlantViewModel
import com.htueko.android.journeyofseed.util.showKeyboard
import kotlinx.android.synthetic.main.activity_insert.*


class InsertActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(PlantViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        btn_save_insert.setOnClickListener {
            val name = textinput_name_insert.editText?.text.toString().trim()
            val location = textinput_location_insert.editText?.text.toString().trim()
            validate(name, location)
        }

        btn_cancel_insert.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

    }

    private fun validate(name: String, location: String) {
        if (name.isNotEmpty() && location.isNotEmpty()) {
            // input are not empty
            viewModel.insertOrUpdate(PlantModel(name, location, ""))
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        } else {
            // one input is empty
            // check name first
            if (name.isEmpty()) {
                textinput_name_insert.apply {
                    error = resources.getString(R.string.text_name_error)
                    requestFocus()
                    showKeyboard(this@InsertActivity)
                }
            } else {
                // name is not empty, so location must be empty
                textinput_location_insert.apply {
                    error = resources.getString(R.string.text_location_error)
                    requestFocus()
                    showKeyboard(this@InsertActivity)
                }
            }
        }
    }

}
