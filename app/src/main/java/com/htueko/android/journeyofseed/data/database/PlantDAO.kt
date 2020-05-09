package com.htueko.android.journeyofseed.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.htueko.android.journeyofseed.data.database.entity.PlantModel

@Dao
interface PlantDAO {

    // to insert or update the plant
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(plant: PlantModel)

    // to delete a single plant
    @Delete
    suspend fun deletePlant(plant: PlantModel)

    // to delete all plant
    @Query("delete from plant_table")
    suspend fun deleteAll()

    // to get all items, return LiveData of list of item
    @Query("select * from plant_table")
    fun getAllPlants(): LiveData<List<PlantModel>>

    // to get plant with name
    @Query("select * from plant_table where (:name)")
    fun getPlantByName(name: String): LiveData<List<PlantModel>>


    // to get plants with location


    // to get plant with weather


    // to get plant with update time ( latest added plant )

}