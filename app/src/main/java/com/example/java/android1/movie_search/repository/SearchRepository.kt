package com.example.java.android1.movie_search.repository

import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import kotlinx.coroutines.flow.Flow

/**
 * The remote repository to get a movie by name from TMDB API
 */

interface SearchRepository {
    fun getMoviesBySearchFromRemoteServer(query: String): Flow<PagingData<MovieDataTMDB>>
}