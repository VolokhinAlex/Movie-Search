package com.example.java.android1.movie_search.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.java.android1.movie_search.repository.RemoteDataSource
import retrofit2.HttpException
import java.io.IOException

class SearchPageSource(
    private val remoteDataSource: RemoteDataSource,
    private val query: String,
) : PagingSource<Int, MovieDataTMDB>() {

    override fun getRefreshKey(state: PagingState<Int, MovieDataTMDB>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDataTMDB> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        val page: Int = params.key ?: 1
        return try {
            val response = remoteDataSource.getMoviesBySearch(
                language = "en-EN",
                page = page,
                adult = false,
                query = query
            )
            val movies = checkNotNull(response.body())
            val nextKey = if (movies.results.isEmpty()) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(data = movies.results, prevKey = prevKey, nextKey = nextKey)
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
