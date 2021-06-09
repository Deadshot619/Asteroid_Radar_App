package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.DataProvider
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.request.FeedDataRequest
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.database.asDatabaseModel
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.utils.getCurrentDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository(val dataSource: AsteroidDatabaseDao) {

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    suspend fun refreshAsteroids(request: FeedDataRequest){
        withContext(Dispatchers.IO){
            DataProvider.getAsteroidsData(
                dataSource = dataSource,
                request = request,
                success = {
                    dataSource.deleteOldData()
                    dataSource.insertAll(it.asDatabaseModel())
                    _asteroids.postValue(dataSource.getTodayAsteroids().asDomainModel())
                },
                error = {}
            )
        }
    }

    suspend fun getTodayAsteroidData(){
        withContext(Dispatchers.IO){
            _asteroids.postValue(dataSource.getTodayAsteroids().asDomainModel())
        }
    }

    suspend fun getWeekAsteroidData(){
        withContext(Dispatchers.IO){
            _asteroids.postValue(dataSource.getWeekAsteroid().asDomainModel())
        }
    }

    suspend fun getAllAsteroidData(){
        withContext(Dispatchers.IO){
            _asteroids.postValue(dataSource.getAllAsteroids().asDomainModel())
        }
    }
}