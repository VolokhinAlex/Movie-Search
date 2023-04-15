package com.example.java.android1.movie_search.repository.category

import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import kotlinx.coroutines.flow.Flow

/**
 * Interface for getting the movie category
 */

interface CategoryRepository {
    fun getCategoryMovies(categoryMovies: String): Flow<PagingData<MovieDataTMDB>>
}