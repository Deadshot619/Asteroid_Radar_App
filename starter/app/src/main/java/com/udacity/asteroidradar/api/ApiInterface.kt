package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.utils.Constants
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(Constants.FEED)
    fun getNeowsAsteroidDataAsync(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Deferred</*ArrayList<Asteroid>*/JSONObject>
}