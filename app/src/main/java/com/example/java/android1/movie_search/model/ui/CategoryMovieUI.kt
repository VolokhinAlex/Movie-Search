package com.example.java.android1.movie_search.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * A class for storing category movies
 * @param category - Category name
 * @param movie - List of films of the category
 */

@Parcelize
data class CategoryMovieUI(val category: String, val movie: List<MovieUI>) : Parcelable