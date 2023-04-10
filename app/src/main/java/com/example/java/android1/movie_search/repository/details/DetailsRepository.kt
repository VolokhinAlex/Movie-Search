package com.example.java.android1.movie_search.repository.details

import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import kotlinx.coroutines.flow.Flow

/**
 * The remote repository to get the details data of movie from Remote Server
 */

interface DetailsRepository {
    suspend fun getMovieDetails(
        movieId: Int,
        language: String
    ): MovieDataTMDB

    fun getSimilarMovies(movieId: Int): Flow<PagingData<MovieDataTMDB>>

    suspend fun getMovieFromLocalSource(movieId: Int): MovieDataRoom

    suspend fun saveMovie(movieDataTMDB: MovieDataTMDB)

    suspend fun updateMovie(movieId: Int, favorite: Boolean)

}