package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import retrofit2.Callback

/**
 * The remote repository to get a movie by name from TMDB API
 */

interface SearchRepository {

    fun getMoviesFromRemoteServer(
        language: String,
        page: Int,
        adult: Boolean,
        query: String,
        callback: Callback<CategoryMoviesTMDB>
    )

}