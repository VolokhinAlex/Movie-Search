package com.example.java.android1.movie_search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.model.SearchPageSource
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of the interface for getting data from Remote Server
 */

class SearchRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {

    /**
     * Method for getting a list of movies by name
     * @param query - The name of a movie to search
     */

    override fun getMoviesBySearchFromRemoteServer(query: String): Flow<PagingData<MovieDataTMDB>> =
        Pager(
            config = PagingConfig(
                pageSize = CategoryRepositoryImpl.CATEGORY_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchPageSource(
                    remoteDataSource = remoteDataSource,
                    query = query
                )
            }
        ).flow

}