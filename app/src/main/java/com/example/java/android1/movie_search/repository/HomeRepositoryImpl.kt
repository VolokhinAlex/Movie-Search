package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import retrofit2.Response

/**
 * Implementation of the interface for getting data from TMDB API
 */

class HomeRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : HomeRepository {

    override suspend fun getCategoryMoviesFromRemoteServer(
        category: String,
        language: String,
        page: Int
    ): Response<CategoryMoviesTMDB> = remoteDataSource.getCategoryMovies(category, language, page)

}