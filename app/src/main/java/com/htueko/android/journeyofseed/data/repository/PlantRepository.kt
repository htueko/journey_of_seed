package com.htueko.android.journeyofseed.data.repository

import com.htueko.android.journeyofseed.data.database.PlantDAO
import com.htueko.android.journeyofseed.data.database.entity.PlantModel

class PlantRepository(private val plantDAO: PlantDAO) {

//    companion object {
//        private var INSTANCE: PlantRepository? = null
//
//        fun initialize(context: Context) {
//            if (INSTANCE == null) {
//                INSTANCE = PlantRepository(context)
//            }
//        }
//
//        fun get(): PlantRepository {
//            return INSTANCE ?: throw IllegalStateException("PlantRepository must be initialized")
//        }
//    }

    // get the database object
    //private val database = PlantDatabase.getDatabase(context, )

    // get the DAO object
    //private val plantDAO = database.getPlantDao()

    // to insert into database (if exist update)
    suspend fun insertOrUpdate(plant: PlantModel) = plantDAO.insertOrUpdate(plant)

    // to delete single item from database
    suspend fun deletePlant(plant: PlantModel) = plantDAO.deletePlant(plant)

    // to get all the item from database
    // this is not suspend function, otherwise not using Coroutine here
    fun getAllPlants() = plantDAO.getAllPlants()


}