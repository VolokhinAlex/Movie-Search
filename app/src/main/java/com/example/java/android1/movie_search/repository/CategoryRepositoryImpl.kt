package com.example.java.android1.movie_search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.CategoryPageSource
import com.example.java.android1.movie_search.model.MovieDataTMDB
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(private val remoteDataSource: RemoteDataSource) : CategoryRepository {
    override fun getCategoryMoviesFromRemoteServer(query: String): Flow<PagingData<MovieDataTMDB>> {
        return Pager(
            config = PagingConfig(
                pageSize = CATEGORY_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CategoryPageSource(remoteDataSource, query) }
        ).flow
    }
    companion object {
        const val CATEGORY_PAGE_SIZE = 5
    }
}
