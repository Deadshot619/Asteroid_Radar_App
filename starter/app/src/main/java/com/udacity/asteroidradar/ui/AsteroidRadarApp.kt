package com.udacity.asteroidradar.ui

import android.app.Application

class AsteroidRadarApp: Application() {

    companion object{
        lateinit var instance: AsteroidRadarApp
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}