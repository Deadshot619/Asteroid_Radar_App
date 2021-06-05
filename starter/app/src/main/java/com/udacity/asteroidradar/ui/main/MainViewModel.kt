package com.udacity.asteroidradar.ui.main

import androidx.lifecycle.*
import com.udacity.asteroidradar.api.DataProvider
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.request.FeedDataRequest
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.database.toAsteroid
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.utils.Status
import com.udacity.asteroidradar.utils.getCurrentDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val dataSource: AsteroidDatabaseDao) : ViewModel() {

    private var asteroidJob: Job? = null
    private var pictureOfDayJob: Job? = null

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _loading = MutableLiveData<Status>()
    val loading: LiveData<Status>
        get() = _loading

    init {
//        generateDummyAsteroidData()
        fetchAsteroidsData()
        fetchPictureOfTheDay()
        getTodayAsteroidsDataFromDb()
    }

    private fun generateDummyAsteroidData() {
        val tempDummyAsteroids = mutableListOf<Asteroid>()
        for (i in 1..10) {
            tempDummyAsteroids.add(
                Asteroid(
                    id = i.toLong(), codename = i.toString(), closeApproachDate = i.toString(),
                    absoluteMagnitude = i.toDouble(), estimatedDiameter = i.toDouble(),
                    relativeVelocity = i.toDouble(), distanceFromEarth = i.toDouble(),
                    isPotentiallyHazardous = i % 2 == 0
                )
            )
        }
//        _dummyAsteroids.value = tempDummyAsteroids
    }

    private fun fetchAsteroidsData() {
        asteroidJob?.cancel()

        asteroidJob = viewModelScope.launch(Dispatchers.IO) {
            val dates = getNextSevenDaysFormattedDates()

            DataProvider.getAsteroidsData(
                dataSource = dataSource,
                request = FeedDataRequest(startDate = dates[0], endDate = dates[dates.size - 1]),
                success = {
                    getTodayAsteroidsDataFromDb()
                },
                error = {}
            )

        }
    }

    private fun fetchPictureOfTheDay() {
        pictureOfDayJob?.cancel()

        pictureOfDayJob = viewModelScope.launch(Dispatchers.IO) {
            DataProvider.getPictureOfTheDay(
                success = {
                    _pictureOfDay.postValue(it)
                },
                error = {}
            )
        }
    }

    fun getTodayAsteroidsDataFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            _asteroids.postValue(dataSource.getTodayAsteroids(todayDate = getCurrentDay()).map { it.toAsteroid() })
        }
    }

    fun getWeekAsteroidsDataFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            _asteroids.postValue(dataSource.getWeekAsteroid(todayDate = getCurrentDay()).map { it.toAsteroid() })
        }
    }

    private fun clearJobs() {
        asteroidJob?.cancel()
        pictureOfDayJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        clearJobs()
    }
}