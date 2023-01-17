package com.example.java.android1.movie_search.repository

import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import kotlinx.coroutines.flow.Flow
import retrofit2.Callback

/**
 * The remote repository to get the details data of movie from Remote Server
 */

interface DetailsRepository {
    fun getMovieDetailsFromRemoteServer(
        movieId: Int,
        language: String,
        callback: Callback<MovieDataTMDB>
    )

    fun getSimilarMoviesFromRemoteServer(movieId: Int): Flow<PagingData<MovieDataTMDB>>
}