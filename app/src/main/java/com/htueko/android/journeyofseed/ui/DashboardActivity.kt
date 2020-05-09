package com.htueko.android.journeyofseed.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.htueko.android.journeyofseed.R
import com.htueko.android.journeyofseed.data.adapter.PlantAdapter
import com.htueko.android.journeyofseed.ui.viewmodel.PlantViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(PlantViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar_dashboard)

        val mAdapter = PlantAdapter(this)
        recycler_view_dashboard.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@DashboardActivity)
        }

        viewModel.getAllPlants().observe(this, Observer { plantList ->
            // update the cache copy of the plantList in the adapter.
            plantList.let { mAdapter.addPlant(it) }
        })

//        fab_dashboard.setOnClickListener {
//            DialogAddItem(this, object : AddItemDialogListener {
//                override fun onSaveButtonClicked(plant: PlantModel) {
//                    viewModel.insertOrUpdate(plant)
//                }
//
//            }).show()
//        }

        fab_dashboard.setOnClickListener {
            startActivity(Intent(this, InsertActivity::class.java))
            finish()
        }

    }

}
