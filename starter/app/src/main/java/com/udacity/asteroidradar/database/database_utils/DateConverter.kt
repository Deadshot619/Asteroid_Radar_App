package com.udacity.asteroidradar.database.database_utils

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.room.TypeConverter
import com.udacity.asteroidradar.utils.Constants
import java.util.*

@SuppressLint("NewApi")
class DateConverter {
    @TypeConverter
    fun toDate(dateString: Long?): Date? {
        return dateString?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}