package com.example.java.android1.movie_search.repository.category

import androidx.paging.PagingData
import com.example.java.android1.movie_search.datasource.category.CategoryDataSource
import com.example.java.android1.movie_search.model.MovieDataTMDB
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of the interface for getting category data from Remote Server
 */

class CategoryRepositoryImpl(
    private val remoteDataSource: CategoryDataSource<PagingData<MovieDataTMDB>>
) : CategoryRepository {

    /**
     * Method for getting category movies from the remote server
     * @param categoryMovies - The category of movies to get
     */

    override fun getCategoryMovies(categoryMovies: String): Flow<PagingData<MovieDataTMDB>> {
        return remoteDataSource.getCategoryMovies(categoryMovies = categoryMovies)
    }
}
