package com.volokhinaleksey.movie_club.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.data.RemoteSimilarPageSource
import com.volokhinaleksey.movie_club.data.mappers.toMovieUI
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import kotlinx.coroutines.flow.Flow

class DetailsApiRepository(
    private val apiHolder: CoreApi
) : DetailsRepository {
    override suspend fun getMovieDetails(movieId: Int, language: String): MovieUI {
        return apiHolder.moviesApi.getMovieDetails(
            movieId = movieId,
            language = language,
            extraRequests = "credits,videos"
        ).toMovieUI()
    }

    override fun getSimilarMovies(movieId: Int): Flow<PagingData<MovieUI>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RemoteSimilarPageSource(
                    apiHolder = apiHolder,
                    movieId = movieId
                )
            }
        ).flow
    }
}