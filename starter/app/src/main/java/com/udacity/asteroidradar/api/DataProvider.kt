package com.udacity.asteroidradar.api

import android.util.Log
import com.udacity.asteroidradar.api.request.FeedDataRequest
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.database.asDatabaseModel
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.utils.Constants

object DataProvider {

    suspend fun getAsteroidsData(
        dataSource: AsteroidDatabaseDao,
        request: FeedDataRequest,
        success: (ArrayList<Asteroid>) -> Unit,
        error: (Exception) -> Unit
    ){
        try {
            val result = ApiClient.mServices.getNeowsAsteroidDataAsync(
                startDate = request.startDate, endDate = request.endDate, apiKey = Constants.API_KEY
            ).await()

            dataSource.deleteOldData()
            dataSource.insertAll(parseAsteroidsJsonResult(result).asDatabaseModel())

            success(parseAsteroidsJsonResult(result))
        } catch (e: Exception){
            error(e)
            Log.e("AsteroidError", e.message.toString())
        }
    }

    suspend fun getPictureOfTheDay(
        success: (PictureOfDay) -> Unit,
        error: (Exception) -> Unit
    ){
        try {
            val result = ApiClient.mServices.getPictureOfTheDayAsync(apiKey = Constants.API_KEY).await()
            success(result)
        } catch (e: Exception){
            error(e)
        }
    }
}