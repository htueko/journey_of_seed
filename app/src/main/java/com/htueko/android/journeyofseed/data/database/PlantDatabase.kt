package com.htueko.android.journeyofseed.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.htueko.android.journeyofseed.data.database.entity.PlantModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val DATABASE_NAME = "plant_database"

@Database(entities = [PlantModel::class], version = 1, exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {

    abstract fun getPlantDao(): PlantDAO

//    companion object {
//        @Volatile
//        private var instance: PlantDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) =
//            instance
//                ?: synchronized(LOCK) {
//                    instance
//                        ?: createDatabase(
//                            context
//                        )
//                            .also { instance = it }
//                }
//
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                PlantDatabase::class.java, DATABASE_NAME
//            ).build()
//
//    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PlantDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PlantDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class PlantDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var plantDao = database.getPlantDao()

                    // Delete all content here.
                    plantDao.deleteAll()

                    // Add sample words.
                    var plant = PlantModel("Banana", "Myanmar", "")
                    plantDao.insertOrUpdate(plant)

                }
            }
        }
    }

}