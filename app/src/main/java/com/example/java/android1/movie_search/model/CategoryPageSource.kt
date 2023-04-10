package com.example.java.android1.movie_search.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.java.android1.movie_search.network.ApiHolder
import com.example.java.android1.movie_search.view.LanguageQuery
import retrofit2.HttpException
import java.io.IOException

/**
 * Needed to navigate through the pages of the list
 * @param apiHolder - A class with methods for getting data from a remote server
 * @param category - A category movies to be received from a remote server
 */

class CategoryPageSource(
    private val apiHolder: ApiHolder,
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
        return try {
            val serverResponse =
                apiHolder.moviesApi.getCategoryMovies(
                    category = category,
                    language = LanguageQuery.EN.languageQuery,
                    page = page
                )
            val nextKey = if (serverResponse.results.isEmpty()) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(data = serverResponse.results, prevKey = prevKey, nextKey = nextKey)
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}

