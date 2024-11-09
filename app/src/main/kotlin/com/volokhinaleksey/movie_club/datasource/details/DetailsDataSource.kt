package com.volokhinaleksey.movie_club.datasource.details

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface DetailsDataSource<T : Any> {

    suspend fun getMovieDetails(
        movieId: Int,
        language: String
    ): T

    fun getSimilarMovies(movieId: Int): Flow<PagingData<T>>

}