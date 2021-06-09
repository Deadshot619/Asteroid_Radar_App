package com.udacity.asteroidradar.ui.main

import androidx.lifecycle.*
import com.udacity.asteroidradar.api.DataProvider
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.request.FeedDataRequest
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.utils.Status
import com.udacity.asteroidradar.utils.getCurrentDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(dataSource: AsteroidDatabaseDao) : ViewModel() {
    private val repository = AsteroidRepository(dataSource = dataSource)

    val asteroids = repository.asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _loading = MutableLiveData<Status>()
    val loading: LiveData<Status>
        get() = _loading

    init {
        viewModelScope.launch {
            val dates = getNextSevenDaysFormattedDates()
            repository.refreshAsteroids(FeedDataRequest(startDate = dates[0], endDate = dates[dates.size - 1]))
        }

        fetchPictureOfTheDay()
        getTodayAsteroidsDataFromDb()
    }

    private fun fetchPictureOfTheDay() {
       viewModelScope.launch(Dispatchers.IO) {
            DataProvider.getPictureOfTheDay(
                success = {
                    _pictureOfDay.postValue(it)
                },
                error = {}
            )
        }
    }

    fun getTodayAsteroidsDataFromDb() {
        viewModelScope.launch {
            repository.getTodayAsteroidData()
        }
    }

    fun getWeekAsteroidsDataFromDb() {
        viewModelScope.launch {
            repository.getWeekAsteroidData()
        }
    }

    fun getAllAsteroidsDataFromDb() {
        viewModelScope.launch {
            repository.getAllAsteroidData()
        }
    }
}