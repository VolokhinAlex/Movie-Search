package com.volokhinaleksey.movie_club.datasource.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.datasource.category.RemoteCategoryDataSource.Companion.CATEGORY_PAGE_SIZE
import com.volokhinaleksey.movie_club.datasource.pagesource.RemoteSearchPageSource
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.network.ApiHolder
import kotlinx.coroutines.flow.Flow

class RemoteSearchDataSource(
    private val apiHolder: ApiHolder
) : SearchDataSource<PagingData<MovieDataTMDB>> {

    override fun getMoviesByQuery(query: String): Flow<PagingData<MovieDataTMDB>> {
        return Pager(
            config = PagingConfig(
                pageSize = CATEGORY_PAGE_SIZE,
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