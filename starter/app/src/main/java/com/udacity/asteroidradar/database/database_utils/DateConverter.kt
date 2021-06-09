package com.udacity.asteroidradar.database.database_utils

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.room.TypeConverter
import com.udacity.asteroidradar.utils.Constants
import java.util.*

@SuppressLint("NewApi")
class DateConverter {
    @TypeConverter
    fun toDate(dateString: String?): Date? {
        return dateString?.let { SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).parse(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): String? {
        return SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(date)
    }
}