package com.volokhinaleksey.movie_club.datasource.details

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.datasource.category.RemoteCategoryDataSource.Companion.CATEGORY_PAGE_SIZE
import com.volokhinaleksey.movie_club.datasource.pagesource.RemoteSimilarPageSource
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.network.ApiHolder
import kotlinx.coroutines.flow.Flow

class RemoteDetailsDataSource(
    private val apiHolder: ApiHolder
) : DetailsDataSource<MovieDataTMDB> {
    override suspend fun getMovieDetails(movieId: Int, language: String): MovieDataTMDB {
        return apiHolder.moviesApi.getMovieDetails(
            movieId = movieId,
            language = language,
            extraRequests = "credits,videos"
        )
    }

    override fun getSimilarMovies(movieId: Int): Flow<PagingData<MovieDataTMDB>> {
        return Pager(
            config = PagingConfig(
                pageSize = CATEGORY_PAGE_SIZE,
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