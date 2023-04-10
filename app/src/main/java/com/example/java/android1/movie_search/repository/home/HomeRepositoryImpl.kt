package com.example.java.android1.movie_search.repository.home

import com.example.java.android1.movie_search.datasource.home.HomeDataSource
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB

/**
 * Implementation of the interface for getting data from Remote Server
 */

class HomeRepositoryImpl(
    private val dataSource: HomeDataSource<CategoryMoviesTMDB>
) : HomeRepository {

    /**
     * Method for getting category movies from the data source
     * @param category - The category of movies to get
     * @param language - Response language
     * @param page - The page to request
     */

    override suspend fun getCategoryMoviesFromRemoteServer(
        category: String,
        language: String,
        page: Int,
        isNetworkAvailable: Boolean
    ): CategoryMoviesTMDB {
        return if (isNetworkAvailable) {
            dataSource.getMovies(category = category, language = language, page = page)
        } else {
            dataSource.getMovies(category = category, language = language, page = page)
        }
    }

}