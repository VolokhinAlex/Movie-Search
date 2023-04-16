package com.example.java.android1.movie_search.datasource.category

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.java.android1.movie_search.datasource.pagesource.RemoteCategoryPageSource
import com.example.java.android1.movie_search.model.remote.MovieDataTMDB
import com.example.java.android1.movie_search.network.ApiHolder
import kotlinx.coroutines.flow.Flow

class RemoteCategoryDataSource(
    private val apiHolder: ApiHolder
) : CategoryDataSource<PagingData<MovieDataTMDB>> {

    override fun getCategoryMovies(categoryMovies: String): Flow<PagingData<MovieDataTMDB>> {
        return Pager(
            config = PagingConfig(
                pageSize = CATEGORY_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RemoteCategoryPageSource(
                    apiHolder = apiHolder,
                    category = categoryMovies
                )
            }
        ).flow
    }

    companion object {
        const val CATEGORY_PAGE_SIZE = 5
    }

}