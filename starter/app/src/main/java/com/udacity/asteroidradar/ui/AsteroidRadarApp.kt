package com.udacity.asteroidradar.ui

import android.app.Application
import com.udacity.asteroidradar.background.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AsteroidRadarApp: Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    companion object{
        lateinit var instance: AsteroidRadarApp
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        delayedInit()
    }

    private fun delayedInit() = applicationScope.launch {
        RefreshDataWorker.startRecurringWork()
    }
}