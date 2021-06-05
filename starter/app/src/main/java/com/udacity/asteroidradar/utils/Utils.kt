package com.udacity.asteroidradar.utils

import java.util.*

fun getCurrentDay(): Date {
    return Calendar.getInstance(Locale.getDefault()).apply {
        set(Calendar.HOUR_OF_DAY,0);
        set(Calendar.MINUTE,0);
        set(Calendar.SECOND,0);
        set(Calendar.MILLISECOND,0);
    }.time
}