package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import retrofit2.Callback

/**
 * The remote repository to get the categories of movies from TMDB API
 */

interface HomeRepository {

    fun getPopularMoviesFromRemoteServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    )

    fun getNowPlayingMoviesFromRemoteServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    )

    fun getTopRatedMoviesFromRemoteServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    )

    fun getUpcomingMoviesFromRemoteServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    )

    fun getMoviesCategoryForCompose(
        category: String,
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    )

}