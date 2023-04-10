package com.example.java.android1.movie_search.repository.details

import androidx.paging.PagingData
import com.example.java.android1.movie_search.datasource.details.DetailsDataSource
import com.example.java.android1.movie_search.datasource.details.LocalDetailsDataSource
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of the interface for getting data from Remote Server
 */

class DetailsRepositoryImpl(
    private val remoteDataSource: DetailsDataSource,
    private val localDataSource: LocalDetailsDataSource
) : DetailsRepository {

    /**
     * Method for getting details of movie from the remote server
     * @param movieId - ID of the movie to get details about it
     * @param language - Response language
     */

    override suspend fun getMovieDetails(
        movieId: Int,
        language: String
    ): MovieDataTMDB {
        return remoteDataSource.getMovieDetails(
            movieId = movieId,
            language = language
        )
    }

    /**
     * Method for getting similar movies from the remote server
     * @param movieId - The current ID of the movie that similar movies will be searched for
     */

    override fun getSimilarMovies(movieId: Int): Flow<PagingData<MovieDataTMDB>> {
        return remoteDataSource.getSimilarMovies(movieId = movieId)
    }

    override suspend fun getMovieFromLocalSource(movieId: Int): MovieDataRoom {
        return localDataSource.getMovieFromLocalDataBase(movieId)
    }

    override suspend fun saveMovie(movieDataTMDB: MovieDataTMDB) {
        localDataSource.saveMovieToLocalDataBase(movieDataTMDB)
    }

    override suspend fun updateMovie(movieId: Int, favorite: Boolean) {
        localDataSource.updateMovieFavoriteInLocalDataBase(movieId, favorite)
    }

}