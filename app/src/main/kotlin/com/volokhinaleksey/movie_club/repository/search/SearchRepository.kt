package com.volokhinaleksey.movie_club.repository.search

import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.model.ui.MovieUI
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