package com.udacity.asteroidradar.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.DataProvider
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.request.FeedDataRequest
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var job: Job? = null

    private val _dummyAsteroids = MutableLiveData<List<Asteroid>>()
    val dummyAsteroids: LiveData<List<Asteroid>>
        get() = _dummyAsteroids

    init {
        generateDummyAsteroidData()
        fetchAsteroidsData()
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
        job?.cancel()

        job = viewModelScope.launch(Dispatchers.IO) {
            val dates = getNextSevenDaysFormattedDates()

            DataProvider.getAsteroidsData(
                request = FeedDataRequest(startDate = dates[0], endDate = dates[dates.size-1]),
                success = {
                    _dummyAsteroids.postValue(it)
                },
                error = {

                }
            )

        }
    }
}