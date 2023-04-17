package com.example.java.android1.movie_search.datasource.details

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.java.android1.movie_search.datasource.category.RemoteCategoryDataSource.Companion.CATEGORY_PAGE_SIZE
import com.example.java.android1.movie_search.datasource.pagesource.SimilarPageSource
import com.example.java.android1.movie_search.model.remote.MovieDataTMDB
import com.example.java.android1.movie_search.network.ApiHolder
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
                SimilarPageSource(
                    apiHolder = apiHolder,
                    movieId = movieId
                )
            }
        ).flow
    }
}