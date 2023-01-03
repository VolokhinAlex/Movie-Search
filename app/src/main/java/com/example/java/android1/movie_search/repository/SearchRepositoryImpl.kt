package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import retrofit2.Callback

/**
 * Implementation of the interface for getting data from TMDB API
 */

class SearchRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {

    override fun getMoviesFromRemoteServer(
        language: String,
        page: Int,
        adult: Boolean,
        query: String,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        remoteDataSource.getMoviesFromSearch(language, page, adult, query, callback)
    }

}