package com.volokhinaleksey.movie_club.repository.details

import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.model.state.MovieState
import com.volokhinaleksey.movie_club.model.ui.MovieUI
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

    fun getSimilarMovies(movieId: Int, isNetworkAvailable: Boolean): Flow<PagingData<MovieUI>>

    suspend fun updateMovie(movieId: Int, favorite: Boolean)

}