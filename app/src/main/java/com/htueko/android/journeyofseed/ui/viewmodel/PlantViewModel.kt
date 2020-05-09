package com.htueko.android.journeyofseed.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.htueko.android.journeyofseed.data.database.PlantDatabase
import com.htueko.android.journeyofseed.data.database.entity.PlantModel
import com.htueko.android.journeyofseed.data.repository.PlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantViewModel(application: Application) : AndroidViewModel(application) {

    // to get the plant repository
    private val repository: PlantRepository

    //val allPlants: LiveData<List<PlantModel>>

    init {
        val plantDao = PlantDatabase.getDatabase(application, viewModelScope).getPlantDao()
        repository = PlantRepository(plantDao)
        //allPlants = repository.getAllPlants()
    }

    // Launching a new coroutine to insert the data in a non-blocking way
    fun insertOrUpdate(plant: PlantModel) =
        viewModelScope.launch(Dispatchers.IO) { repository.insertOrUpdate(plant) }

    fun deletePlant(plant: PlantModel) =
        viewModelScope.launch(Dispatchers.IO) { repository.deletePlant(plant) }

    // to get all items from database
    fun getAllPlants() = repository.getAllPlants()

}