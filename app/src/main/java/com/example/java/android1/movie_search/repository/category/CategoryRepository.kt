package com.example.java.android1.movie_search.repository.category

import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.ui.MovieUI
import kotlinx.coroutines.flow.Flow

/**
 * Interface for getting the movie category
 */

interface CategoryRepository {
    fun getCategoryMovies(
        categoryMovies: String,
        isNetworkAvailable: Boolean
    ): Flow<PagingData<MovieUI>>
}