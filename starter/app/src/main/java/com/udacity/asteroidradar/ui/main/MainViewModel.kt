package com.udacity.asteroidradar.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.DataProvider
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.request.FeedDataRequest
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var asteroidJob: Job? = null
    private var pictureOfDayJob: Job? = null

    private val _dummyAsteroids = MutableLiveData<List<Asteroid>>()
    val dummyAsteroids: LiveData<List<Asteroid>>
        get() = _dummyAsteroids

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
        _dummyAsteroids.value = tempDummyAsteroids
    }

    private fun fetchAsteroidsData(){
        asteroidJob?.cancel()

        asteroidJob = viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(Status.LOADING)
            val dates = getNextSevenDaysFormattedDates()

            DataProvider.getAsteroidsData(
                request = FeedDataRequest(startDate = dates[0], endDate = dates[dates.size-1]),
                success = {
                    _dummyAsteroids.postValue(it)
                    _loading.postValue(Status.SUCCESS)
                },
                error = {
                    _loading.postValue(Status.ERROR)
                }
            )

        }
    }

    private fun fetchPictureOfTheDay(){
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

    private fun clearJobs(){
        asteroidJob?.cancel()
        pictureOfDayJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        clearJobs()
    }
}