package com.example.java.android1.movie_search.repository.details

import androidx.paging.PagingData
import androidx.paging.map
import com.example.java.android1.movie_search.datasource.details.DetailsDataSource
import com.example.java.android1.movie_search.datasource.details.LocalDetailsDataSource
import com.example.java.android1.movie_search.model.old.remote.MovieDataTMDB
import com.example.java.android1.movie_search.model.state.MovieState
import com.example.java.android1.movie_search.model.ui.MovieUI
import com.example.java.android1.movie_search.utils.convertMovieRoomToMovieDto
import com.example.java.android1.movie_search.utils.mapLocalMovieToMovieUI
import com.example.java.android1.movie_search.utils.mapMovieDataTMDBToMovieUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of the interface for getting data from Remote Server
 */

class DetailsRepositoryImpl(
    private val remoteDataSource: DetailsDataSource<MovieDataTMDB>,
    private val localDataSource: LocalDetailsDataSource
) : DetailsRepository {

    /**
     * Method for getting details of movie from the remote server
     * @param movieId - ID of the movie to get details about it
     * @param language - Response language
     */

    override suspend fun getMovieDetails(
        movieId: Int,
        language: String,
        isNetworkAvailable: Boolean,
        category: String
    ): MovieState {
        return if (isNetworkAvailable) {
            val movie = remoteDataSource.getMovieDetails(
                movieId = movieId,
                language = language
            )
            localDataSource.saveMovieToLocalDataBase(movie)
            MovieState.Success(listOf(mapMovieDataTMDBToMovieUI(movie, category)))
        } else {
            MovieState.Success(
                listOf(
                    mapMovieDataTMDBToMovieUI(
                        convertMovieRoomToMovieDto(
                            localDataSource.getMovieFromLocalDataBase(movieId = movieId)
                        ), category
                    )
                )
            )
        }
    }

    /**
     * Method for getting similar movies from the remote server
     * @param movieId - The current ID of the movie that similar movies will be searched for
     */

    override fun getSimilarMovies(movieId: Int): Flow<PagingData<MovieUI>> {
        return remoteDataSource.getSimilarMovies(movieId = movieId).map {
            it.map { movie -> mapMovieDataTMDBToMovieUI(movie, "similar") }
        }
    }

    override suspend fun getMovieFromLocalSource(movieId: Int): MovieUI {
        return mapLocalMovieToMovieUI(localDataSource.getMovieFromLocalDataBase(movieId))
    }

    override suspend fun updateMovie(movieId: Int, favorite: Boolean) {
        localDataSource.updateMovieFavoriteInLocalDataBase(movieId, favorite)
    }

}