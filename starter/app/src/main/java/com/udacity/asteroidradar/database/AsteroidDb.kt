package com.udacity.asteroidradar.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.utils.Constants
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = AsteroidDb.TABLE_NAME)
data class AsteroidDb(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "code_name") val codename: String,
    @ColumnInfo(name = "close_approach_date") val closeApproachDate: Date?,
    @ColumnInfo(name = "absolute_magnitude") val absoluteMagnitude: Double,
    @ColumnInfo(name = "estimated_diameter") val estimatedDiameter: Double,
    @ColumnInfo(name = "relative_velocity") val relativeVelocity: Double,
    @ColumnInfo(name = "distance_from_earth") val distanceFromEarth: Double,
    @ColumnInfo(name = "is_potentially_hazardous") val isPotentiallyHazardous: Boolean
) {
    companion object{
        const val TABLE_NAME = "asteroid_table"
    }
}


fun Asteroid.toAsteroidDb(): AsteroidDb{
    return AsteroidDb(
        id = id,
        codename = codename,
        absoluteMagnitude = absoluteMagnitude,
        estimatedDiameter = estimatedDiameter,
        relativeVelocity = relativeVelocity,
        distanceFromEarth = distanceFromEarth,
        isPotentiallyHazardous = isPotentiallyHazardous,
        closeApproachDate = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).parse(closeApproachDate)!!
    )
}

fun AsteroidDb.toAsteroid(): Asteroid{
    return Asteroid(
        id = id,
        codename = codename,
        absoluteMagnitude = absoluteMagnitude,
        estimatedDiameter = estimatedDiameter,
        relativeVelocity = relativeVelocity,
        distanceFromEarth = distanceFromEarth,
        isPotentiallyHazardous = isPotentiallyHazardous,
        closeApproachDate = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(closeApproachDate)
    )
}