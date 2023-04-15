package com.example.java.android1.movie_search.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreUI(
    val id: Int = 0,
    val name: String,
): Parcelable