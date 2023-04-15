package com.example.java.android1.movie_search.repository.home

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB

/**
 * The remote repository to get the categories of movies from Remote Server
 */

interface HomeRepository {

    suspend fun getCategoryMoviesFromRemoteServer(
        category: String,
        language: String,
        page: Int,
        isNetworkAvailable: Boolean
    ): CategoryMoviesTMDB

}