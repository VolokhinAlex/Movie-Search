package com.example.java.android1.movie_search.repository.search

import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.ui.MovieUI
import kotlinx.coroutines.flow.Flow

/**
 * The remote repository to get a movie by name from Remote Server
 */

interface SearchRepository {
    fun getMoviesBySearch(
        query: String,
        isNetworkAvailable: Boolean
    ): Flow<PagingData<MovieUI>>
}