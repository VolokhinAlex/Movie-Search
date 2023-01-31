package com.example.java.android1.movie_search.repository

import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import kotlinx.coroutines.flow.Flow

/**
 * Interface for getting the movie category
 */

interface CategoryRepository {
    fun getCategoryMoviesFromRemoteServer(categoryMovies: String): Flow<PagingData<MovieDataTMDB>>
}