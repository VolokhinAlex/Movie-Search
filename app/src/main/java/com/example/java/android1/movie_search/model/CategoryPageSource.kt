package com.example.java.android1.movie_search.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.java.android1.movie_search.repository.RemoteDataSource
import retrofit2.HttpException

class CategoryPageSource(
    private val remoteDataSource: RemoteDataSource,
    private val category: String,
) : PagingSource<Int, MovieDataTMDB>() {

    override fun getRefreshKey(state: PagingState<Int, MovieDataTMDB>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDataTMDB> {
        if (category.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        val page: Int = params.key ?: 1
        val response = remoteDataSource.getCategory(category = category, language = "ru-RU", page = page)
        if (response.isSuccessful) {
            val movies = checkNotNull(response.body())
            val nextKey = if (movies.results.isEmpty()) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            return LoadResult.Page(data = movies.results, prevKey = prevKey, nextKey = nextKey)
        } else {
            return LoadResult.Error(HttpException(response))
        }
    }
}

