package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.MovieDataTMDB
import retrofit2.Callback

class DetailsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : DetailsRepository {

    override fun getMovieDetailsFromServer(
        movieId: Int,
        language: String,
        callback: Callback<MovieDataTMDB>
    ) {
        remoteDataSource.getMovieDetails(movieId, language, callback)
    }

}