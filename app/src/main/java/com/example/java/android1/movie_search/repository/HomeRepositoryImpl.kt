package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import retrofit2.Response

/**
 * Implementation of the interface for getting data from Remote Server
 */

class HomeRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : HomeRepository {

    /**
     * Method for getting category movies from the remote server
     * @param category - The category of movies to get
     * @param language - Response language
     * @param page - The page to request
     */

    override suspend fun getCategoryMoviesFromRemoteServer(
        category: String,
        language: String,
        page: Int
    ): Response<CategoryMoviesTMDB> =
        remoteDataSource.getCategoryMovies(category = category, language = language, page = page)

}