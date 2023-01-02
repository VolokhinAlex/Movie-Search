package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.MovieDataTMDB
import retrofit2.Callback

/**
 * Implementation of the interface for getting data from TMDB API
 */

class DetailsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : DetailsRepository {

    override fun getMovieDetailsFromRemoteServer(
        movieId: Int,
        language: String,
        callback: Callback<MovieDataTMDB>
    ) {
        remoteDataSource.getMovieDetails(movieId, language, callback)
    }

}