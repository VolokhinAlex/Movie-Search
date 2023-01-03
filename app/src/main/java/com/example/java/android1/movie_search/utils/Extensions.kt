package com.example.java.android1.movie_search.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.text.SimpleDateFormat
import java.util.*

fun FragmentManager.replace(container: Int, fragment: Fragment) {
    this.apply {
        beginTransaction().replace(container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null).commit()
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun String.getYearFromStringFullDate(stringDate: String): String {
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