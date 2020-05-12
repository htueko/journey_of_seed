package com.htueko.android.journeyofseed.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.htueko.android.journeyofseed.R
import com.htueko.android.journeyofseed.data.adapter.PlantAdapter
import com.htueko.android.journeyofseed.data.adapter.SwipeToDelete
import com.htueko.android.journeyofseed.ui.viewmodel.PlantViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(PlantViewModel::class.java)
    }

    private lateinit var mAdapter: PlantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar_dashboard)

        // init
        mAdapter = PlantAdapter(this)

        recycler_view_dashboard.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@DashboardActivity)
        }

        viewModel.getAllPlants().observe(this, Observer { plantList ->
            // update the cache copy of the plantList in the adapter.
            plantList.let { mAdapter.addPlant(it) }
        })

        swipeToDelete(mAdapter, viewModel, recycler_view_dashboard)

        fab_dashboard.setOnClickListener {
            startActivity(Intent(this, InsertActivity::class.java))
        }

    }

    // to swipe left or right to delete from item from recycler view
    private fun swipeToDelete(
        adapter: PlantAdapter,
        viewModel: PlantViewModel,
        recyclerView: RecyclerView
    ) {
        val swipeHandler = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val plant = adapter.getPlantAt(position)
                viewModel.deletePlant(plant)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}
