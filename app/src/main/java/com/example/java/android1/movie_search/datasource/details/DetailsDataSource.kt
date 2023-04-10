package com.example.java.android1.movie_search.datasource.details

import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import kotlinx.coroutines.flow.Flow

interface DetailsDataSource {

    suspend fun getMovieDetails(
        movieId: Int,
        language: String
    ): MovieDataTMDB

    fun getSimilarMovies(movieId: Int): Flow<PagingData<MovieDataTMDB>>

}