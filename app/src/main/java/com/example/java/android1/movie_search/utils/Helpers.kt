package com.example.java.android1.movie_search.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Converting a full date to a year only
 * @sample 2022/12/12 -> 2022
 */

fun convertStringFullDateToOnlyYear(stringDate: String): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    if (stringDate != "") {
        val date = formatter.parse(stringDate)
        val calendar: Calendar = Calendar.getInstance();
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

fun timeToFormatHoursAndMinutes(min: Int): String {
    val hour = min / 60
    val minutes = min % 60
    return String.format("%02dh %02dmin", hour, minutes)
}