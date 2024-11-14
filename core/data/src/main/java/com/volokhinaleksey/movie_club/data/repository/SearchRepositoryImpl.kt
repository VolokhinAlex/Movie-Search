package com.volokhinaleksey.movie_club.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.data.RemoteSearchPageSource
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getMoviesByQuery(query: String): Flow<PagingData<Movie>>
}

class SearchRepositoryImpl(
    private val apiHolder: CoreApi
) : SearchRepository {

    override fun getMoviesByQuery(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RemoteSearchPageSource(
                    apiHolder = apiHolder,
                    query = query
                )
            }
        ).flow
    }

}