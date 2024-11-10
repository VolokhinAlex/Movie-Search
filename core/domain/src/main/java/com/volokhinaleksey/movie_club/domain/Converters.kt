package com.volokhinaleksey.movie_club.domain

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Converting a full date to a year only
 * @sample 2022/12/12 -> 2022
 */

internal fun convertStringFullDateToOnlyYear(stringDate: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    if (stringDate != "") {
        val date = dateFormat.parse(stringDate)
        val calendar: Calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        return calendar.get(Calendar.YEAR).toString()
    }
    return ""
}