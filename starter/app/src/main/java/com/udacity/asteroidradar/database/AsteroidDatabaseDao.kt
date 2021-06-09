package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.utils.getCurrentDay
import java.util.*

@Dao
interface AsteroidDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroids: List<AsteroidDb>)

    @Query("SELECT * FROM ${AsteroidDb.TABLE_NAME} WHERE close_approach_date = :todayDate")
    fun getTodayAsteroids(todayDate: Date = getCurrentDay()): List<AsteroidDb>

    @Query("SELECT * FROM ${AsteroidDb.TABLE_NAME} WHERE close_approach_date >= :todayDate ORDER BY close_approach_date ASC")
    fun getWeekAsteroid(todayDate: Date = getCurrentDay()): List<AsteroidDb>

    @Query("DELETE FROM ${AsteroidDb.TABLE_NAME} WHERE close_approach_date < :todayDate")
    fun deleteOldData(todayDate: Date = getCurrentDay())

    @Query("SELECT * FROM ${AsteroidDb.TABLE_NAME}")
    fun getAllAsteroids(): List<AsteroidDb>
}