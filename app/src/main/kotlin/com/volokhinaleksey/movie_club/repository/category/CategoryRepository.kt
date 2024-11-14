package com.volokhinaleksey.movie_club.repository.category

import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.model.ui.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Interface for getting the movie category
 */

interface CategoryRepository {
    fun getCategoryMovies(
        categoryMovies: String,
        isNetworkAvailable: Boolean
    ): Flow<PagingData<Movie>>
}