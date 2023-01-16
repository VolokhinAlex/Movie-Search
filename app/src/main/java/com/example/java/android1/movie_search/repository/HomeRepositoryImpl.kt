package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import retrofit2.Callback

/**
 * Implementation of the interface for getting data from TMDB API
 */

class HomeRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : HomeRepository {

    override fun getPopularMoviesFromRemoteServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        remoteDataSource.getPopularMovies(language = language, page = page, callback = callback)
    }

    override fun getNowPlayingMoviesFromRemoteServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        remoteDataSource.getNowPlayingMovies(language = language, page = page, callback = callback)
    }

    override fun getTopRatedMoviesFromRemoteServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        remoteDataSource.getTopRatedMovies(language = language, page = page, callback = callback)
    }

    override fun getUpcomingMoviesFromRemoteServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        remoteDataSource.getUpcomingMovies(language = language, page = page, callback = callback)
    }

    override fun getMoviesCategoryForCompose(
        category: String,
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        remoteDataSource.getMoviesCategoryForCompose(category, language, page, callback)
    }

}