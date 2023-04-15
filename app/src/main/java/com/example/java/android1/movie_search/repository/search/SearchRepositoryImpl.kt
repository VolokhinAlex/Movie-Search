package com.example.java.android1.movie_search.repository.search

import androidx.paging.PagingData
import androidx.paging.map
import com.example.java.android1.movie_search.datasource.search.LocalSearchDataSource
import com.example.java.android1.movie_search.datasource.search.SearchDataSource
import com.example.java.android1.movie_search.model.old.remote.MovieDataTMDB
import com.example.java.android1.movie_search.model.ui.MovieUI
import com.example.java.android1.movie_search.utils.mapMovieDataTMDBToLocalMovieData
import com.example.java.android1.movie_search.utils.mapMovieDataTMDBToMovieUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of the interface for getting data from Remote Server
 */

class SearchRepositoryImpl(
    private val remoteDataSource: SearchDataSource<PagingData<MovieDataTMDB>>,
    private val localDataSource: LocalSearchDataSource
) : SearchRepository {

    /**
     * Method for getting a list of movies by name
     * @param query - The name of a movie to search
     */

    override fun getMoviesBySearch(
        query: String,
        isNetworkAvailable: Boolean
    ): Flow<PagingData<MovieUI>> {
        return remoteDataSource.getMoviesByQuery(query = query).map {
            it.map { movie ->
                localDataSource.saveMovie(movie = mapMovieDataTMDBToLocalMovieData(movie))
                mapMovieDataTMDBToMovieUI(movie)
            }
        }
    }

}