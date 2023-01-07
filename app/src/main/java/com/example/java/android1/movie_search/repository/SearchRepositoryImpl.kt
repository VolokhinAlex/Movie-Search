package com.example.java.android1.movie_search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.java.android1.movie_search.model.CategoryMoviesTMDB
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.model.SearchPageSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Callback

/**
 * Implementation of the interface for getting data from TMDB API
 */

class SearchRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {

    override fun getMoviesFromRemoteServer(
        language: String,
        page: Int,
        adult: Boolean,
        query: String,
        callback: Callback<CategoryMoviesTMDB>
    ) {
        remoteDataSource.getMoviesFromSearch(language, page, adult, query, callback)
    }

    override fun getMoviesBySearchFromRemoteServer(query: String): Flow<PagingData<MovieDataTMDB>> = Pager(
        config = PagingConfig(
            pageSize = CategoryRepositoryImpl.CATEGORY_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { SearchPageSource(remoteDataSource, query) }
    ).flow

}