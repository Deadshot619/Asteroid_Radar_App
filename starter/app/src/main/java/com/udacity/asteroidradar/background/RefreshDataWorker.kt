package com.udacity.asteroidradar.background

import android.content.Context
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.request.FeedDataRequest
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import java.lang.Exception
import java.util.concurrent.TimeUnit

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        private const val WORK_NAME = "RefreshDataWorker"

        fun startRecurringWork() {
            WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                getWorkRequest()
            )
        }

        private fun getWorkRequest(): PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
                .setConstraints(getConstraints())
                .build()


        private fun getConstraints(): Constraints =
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }.build()

    }

    override suspend fun doWork(): Result {
        val dataSource = AsteroidDatabase.getInstance(applicationContext).asteroidDatabaseDao
        val repository = AsteroidRepository(dataSource)
        val dates = getNextSevenDaysFormattedDates()

        return try {
            repository.refreshAsteroids(
                FeedDataRequest(startDate = dates[0], endDate = dates[dates.size - 1])
            )

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}