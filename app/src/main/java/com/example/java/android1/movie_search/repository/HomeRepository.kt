package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import retrofit2.Callback

interface HomeRepository {

    fun getPopularMoviesFromServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    )

    fun getNowPlayingMoviesFromServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    )

    fun getTopRatedMoviesFromServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    )

    fun getUpcomingMoviesFromServer(
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