package com.volokhinaleksey.movie_club.repository.search

import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.datasource.search.LocalSearchDataSource
import com.volokhinaleksey.movie_club.datasource.search.SearchDataSource
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import kotlinx.coroutines.flow.Flow

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
        TODO()
//        return if (isNetworkAvailable) {
//            remoteDataSource.getMoviesByQuery(query = query).map {
//                it.map { movie ->
//                    localDataSource.saveMovie(movie = mapMovieDataTMDBToLocalMovieData(movie))
//                    mapMovieDataTMDBToMovieUI(movie)
//                }
//            }
//        } else {
//            localDataSource.getMoviesByQuery(query = query).map {
//                it.map { movie ->
//                    localDataSource.saveMovie(movie = movie)
//                    mapLocalMovieToMovieUI(localMovieData = movie)
//                }
//            }
//        }
    }

}