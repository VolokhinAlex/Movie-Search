package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import retrofit2.Callback

class HomeRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : HomeRepository {

    override fun getPopularMoviesFromServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        remoteDataSource.getPopularMovies(language = language, page = page, callback = callback)
    }

    override fun getNowPlayingMoviesFromServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        remoteDataSource.getNowPlayingMovies(language = language, page = page, callback = callback)
    }

    override fun getTopRatedMoviesFromServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        remoteDataSource.getTopRatedMovies(language = language, page = page, callback = callback)
    }

    override fun getUpcomingMoviesFromServer(
        language: String,
        page: Int,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        remoteDataSource.getUpcomingMovies(language = language, page = page, callback = callback)
    }

}