package com.example.java.android1.movie_search.datasource.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.java.android1.movie_search.datasource.category.RemoteCategoryDataSource.Companion.CATEGORY_PAGE_SIZE
import com.example.java.android1.movie_search.datasource.pagesource.RemoteSearchPageSource
import com.example.java.android1.movie_search.model.remote.MovieDataTMDB
import com.example.java.android1.movie_search.network.ApiHolder
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