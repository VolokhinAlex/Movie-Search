package com.example.java.android1.movie_search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.CategoryPageSource
import com.example.java.android1.movie_search.model.MovieDataTMDB
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of the interface for getting category data from Remote Server
 */

class CategoryRepositoryImpl(private val remoteDataSource: RemoteDataSource) : CategoryRepository {

    /**
     * Method for getting category movies from the remote server
     * @param categoryMovies - The category of movies to get
     */

    override fun getCategoryMoviesFromRemoteServer(categoryMovies: String): Flow<PagingData<MovieDataTMDB>> {
        return Pager(
            config = PagingConfig(
                pageSize = CATEGORY_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CategoryPageSource(
                    remoteDataSource = remoteDataSource,
                    category = categoryMovies
                )
            }
        ).flow
    }

    companion object {
        const val CATEGORY_PAGE_SIZE = 5
    }
}
