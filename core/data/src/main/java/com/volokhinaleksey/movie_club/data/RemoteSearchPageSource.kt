package com.volokhinaleksey.movie_club.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.volokhinaleksey.movie_club.data.mappers.toMovieUI
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import java.io.IOException

/**
 * Needed to navigate through the pages of the list
 * @param apiHolder - A class with methods for getting data from a remote server
 * @param query - A request to be found and received from a remote server
 */

class RemoteSearchPageSource(
    private val apiHolder: CoreApi,
    private val query: String,
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        val page: Int = params.key ?: 1
        return try {
            val serverResponse = apiHolder.moviesApi.getMoviesBySearch(
                language = "en-EN",
                page = page,
                adult = false,
                query = query
            ).results.map { it.toMovieUI() }
            val nextKey = if (serverResponse.isEmpty()) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(data = serverResponse, prevKey = prevKey, nextKey = nextKey)
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}
