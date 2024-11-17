package com.volokhinaleksey.movie_club.utils

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

fun String.convertStringFullDateToOnlyYear(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    if (this != "") {
        val date = dateFormat.parse(this)
        val calendar: Calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        return calendar.get(Calendar.YEAR).toString()
    }
    return ""
}

/**
 * Minutes are converted to the format of hours and minutes
 * @sample min = 96 -> 1h 36min
 */

fun Int?.timeToFormatHoursAndMinutes(): String {
    val min = this ?: return ""
    val hour = min / 60
    val minutes = min % 60
    return String.format("%02dh %02dmin", hour, minutes)
}