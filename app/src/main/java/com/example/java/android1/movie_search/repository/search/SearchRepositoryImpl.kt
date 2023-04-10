package com.example.java.android1.movie_search.repository.search

import androidx.paging.PagingData
import com.example.java.android1.movie_search.datasource.search.SearchDataSource
import com.example.java.android1.movie_search.model.MovieDataTMDB
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of the interface for getting data from Remote Server
 */

class SearchRepositoryImpl(
    private val remoteDataSource: SearchDataSource<PagingData<MovieDataTMDB>>
) : SearchRepository {

    /**
     * Method for getting a list of movies by name
     * @param query - The name of a movie to search
     */

    override fun getMoviesBySearchFromRemoteServer(query: String): Flow<PagingData<MovieDataTMDB>> {
        return remoteDataSource.getMoviesByQuery(query = query)
    }

}