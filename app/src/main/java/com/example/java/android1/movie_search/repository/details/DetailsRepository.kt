package com.example.java.android1.movie_search.repository.details

import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.state.MovieState
import com.example.java.android1.movie_search.model.ui.MovieUI
import kotlinx.coroutines.flow.Flow

/**
 * The remote repository to get the details data of movie from Remote Server
 */

interface DetailsRepository {
    suspend fun getMovieDetails(
        movieId: Int,
        language: String,
        isNetworkAvailable: Boolean,
        category: String
    ): MovieState

    fun getSimilarMovies(movieId: Int): Flow<PagingData<MovieUI>>

    suspend fun getMovieFromLocalSource(movieId: Int): MovieUI

    suspend fun updateMovie(movieId: Int, favorite: Boolean)

}