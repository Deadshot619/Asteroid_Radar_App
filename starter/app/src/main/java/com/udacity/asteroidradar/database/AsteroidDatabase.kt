package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.*
import com.udacity.asteroidradar.database.database_utils.DateConverter

@Database(entities = [AsteroidDb::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AsteroidDatabase: RoomDatabase() {

    abstract val asteroidDatabaseDao: AsteroidDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        private const val DATABASE_NAME = "asteroid_database"

        fun getInstance(context: Context): AsteroidDatabase{
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }

    }
}