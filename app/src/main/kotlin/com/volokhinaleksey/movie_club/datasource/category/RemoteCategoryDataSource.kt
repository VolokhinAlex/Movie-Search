package com.volokhinaleksey.movie_club.datasource.category

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.datasource.pagesource.RemoteCategoryPageSource
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import kotlinx.coroutines.flow.Flow

class RemoteCategoryDataSource(
    private val apiHolder: CoreApi
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