package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import retrofit2.Callback

class SearchRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {

    override fun getMoviesFromServer(
        language: String,
        page: Int,
        query: String,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        remoteDataSource.getMoviesFromSearch(language, page, query, callback)
    }

}