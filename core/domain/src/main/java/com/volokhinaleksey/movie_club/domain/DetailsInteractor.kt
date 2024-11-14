package com.volokhinaleksey.movie_club.domain

import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.model.state.MovieState
import com.volokhinaleksey.movie_club.model.ui.Favorite
import com.volokhinaleksey.movie_club.model.ui.Movie
import kotlinx.coroutines.flow.Flow

interface DetailsInteractor {

    suspend fun getMovieDetails(
        movieId: Int,
        language: String,
        isNetworkAvailable: Boolean,
        category: String = ""
    ): MovieState

    fun getSimilarMovies(movieId: Int, isNetworkAvailable: Boolean): Flow<PagingData<Movie>>

    suspend fun saveFavoriteMovie(favorite: Favorite)

}