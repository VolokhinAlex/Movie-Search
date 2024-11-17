package com.volokhinaleksey.movie_club.data.repository

import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.model.ui.Favorite
import com.volokhinaleksey.movie_club.model.ui.Movie
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    suspend fun getMovieDetails(movieId: Int): Movie

    fun getSimilarMovies(movieId: Int, language: String): Flow<PagingData<Movie>>

    suspend fun saveFavoriteMovie(favorite: Favorite)

    suspend fun syncMovieDetails(movieId: Int, category: String, language: String)
}