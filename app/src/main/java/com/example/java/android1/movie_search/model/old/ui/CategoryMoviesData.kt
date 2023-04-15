package com.example.java.android1.movie_search.model.old.ui

import com.example.java.android1.movie_search.model.old.remote.MovieDataTMDB

/**
 * A class for storing category movies
 * @param queryName - Category name
 * @param categoryMoviesData - List of films of the category
 */

data class CategoryMoviesData(val queryName: String, val categoryMoviesData: List<MovieDataTMDB>)