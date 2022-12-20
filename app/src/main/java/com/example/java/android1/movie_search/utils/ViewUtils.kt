package com.example.java.android1.movie_search.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

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