package com.htueko.android.journeyofseed.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htueko.android.journeyofseed.R
import com.htueko.android.journeyofseed.data.database.entity.PlantModel


class PlantAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    // Cached copy of plant list
    private var mPlantList = emptyList<PlantModel>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val that = context

    override fun getItemCount() = mPlantList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = inflater.inflate(R.layout.item_list, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val item = mPlantList[position]
        holder.bind(item)

    }

    internal fun addPlant(plantList: List<PlantModel>) {
        this.mPlantList = plantList
        notifyDataSetChanged()
    }

    // to get the position of the plant item
    fun getPlantAt(position: Int): PlantModel = mPlantList[position]

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // reference to plant model
        private lateinit var mPlant: PlantModel

        // get the references from item_list
        private val name: AppCompatTextView = itemView.findViewById(R.id.tv_name_item)
        private val location: AppCompatTextView = itemView.findViewById(R.id.tv_location_item)
        private val localUrl: AppCompatImageView = itemView.findViewById(R.id.imv_image_item)

        fun bind(plant: PlantModel) {
            mPlant = plant
            name.text = plant.name
            location.text = plant.location
            Glide.with(that).load(plant.localUrl).into(localUrl)
        }

    }

}