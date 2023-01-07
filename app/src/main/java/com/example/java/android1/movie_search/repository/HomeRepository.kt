package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import retrofit2.Callback
import retrofit2.Response

/**
 * The remote repository to get the categories of movies from TMDB API
 */

interface HomeRepository {

    fun getMoviesCategoryFromRemoteServer(
        category: String,
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    )

    suspend fun getCategoryMoviesFromRemoteServer(
        category: String,
        language: String,
        page: Int
    ): Response<CategoryMoviesTMDB>

}