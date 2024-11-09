package com.volokhinaleksey.movie_club.repository.home

import com.volokhinaleksey.movie_club.datasource.home.HomeDataSource
import com.volokhinaleksey.movie_club.datasource.home.LocalHomeDataSource
import com.volokhinaleksey.movie_club.model.remote.CategoryMoviesTMDB
import com.volokhinaleksey.movie_club.model.state.CategoryState
import com.volokhinaleksey.movie_club.utils.mapMovieCategoryMoviesTMDBToCategoryMovieUI

/**
 * Implementation of the interface for getting data from Remote Server
 */

class HomeRepositoryImpl(
    private val dataSource: HomeDataSource<CategoryMoviesTMDB>,
    private val localDataSource: LocalHomeDataSource
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
    ): CategoryState {
        return if (isNetworkAvailable) {
            val movies = dataSource.getMovies(category = category, language = language, page = page)
            localDataSource.saveMovie(movies, category)
            CategoryState.Success(mapMovieCategoryMoviesTMDBToCategoryMovieUI(category, movies))
        } else {
            CategoryState.Success(
                mapMovieCategoryMoviesTMDBToCategoryMovieUI(
                    category,
                    localDataSource.getMovies(category = category, language = language, page = page)
                )
            )
        }
    }

}