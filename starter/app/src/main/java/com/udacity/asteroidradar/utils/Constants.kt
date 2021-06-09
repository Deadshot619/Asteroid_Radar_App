package com.udacity.asteroidradar.utils

import com.udacity.asteroidradar.BuildConfig

object Constants {
    const val API_QUERY_DATE_FORMAT = "yyyy-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"

//    API Key
    //TODO : Put your Api Key in build.gradle
    val API_KEY = BuildConfig.NASA_API_KEY

    //URL Path
    const val FEED = "neo/rest/v1/feed"
    const val PICTURE_OF_DAY = "planetary/apod"
}