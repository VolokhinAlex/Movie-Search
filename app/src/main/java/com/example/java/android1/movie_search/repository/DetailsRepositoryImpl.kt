package com.example.java.android1.movie_search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.model.SimilarPageSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Callback

/**
 * Implementation of the interface for getting data from Remote Server
 */

class DetailsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : DetailsRepository {

    /**
     * Method for getting details of movie from the remote server
     * @param movieId - ID of the movie to get details about it
     * @param language - Response language
     */

    override fun getMovieDetailsFromRemoteServer(
        movieId: Int,
        language: String,
        callback: Callback<MovieDataTMDB>
    ) {
        remoteDataSource.getMovieDetails(
            movieId = movieId,
            language = language,
            callback = callback
        )
    }

    /**
     * Method for getting similar movies from the remote server
     * @param movieId - The current ID of the movie that similar movies will be searched for
     */

    override fun getSimilarMoviesFromRemoteServer(movieId: Int): Flow<PagingData<MovieDataTMDB>> =
        Pager(
            config = PagingConfig(
                pageSize = CategoryRepositoryImpl.CATEGORY_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SimilarPageSource(
                    remoteDataSource = remoteDataSource,
                    movieId = movieId
                )
            }
        ).flow

}