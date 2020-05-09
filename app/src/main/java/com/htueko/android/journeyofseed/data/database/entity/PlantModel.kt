package com.htueko.android.journeyofseed.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_table")
data class PlantModel(
    @ColumnInfo(name = "plant_name")
    var name: String,
    @ColumnInfo(name = "plant_location")
    var location: String,
    @ColumnInfo(name = "plant_local_url")
    var localUrl: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}