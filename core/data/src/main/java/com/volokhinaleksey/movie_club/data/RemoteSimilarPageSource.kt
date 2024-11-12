package com.volokhinaleksey.movie_club.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.volokhinaleksey.movie_club.data.mappers.toMovieUI
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import java.io.IOException

/**
 * Needed to navigate through the pages of the list
 * @param apiHolder - A class with methods for getting data from a remote server
 * @param movieId - A Movie ID that similar movies will be searched for
 */

class RemoteSimilarPageSource(
    private val apiHolder: CoreApi,
    private val movieId: Int,
) : PagingSource<Int, MovieUI>() {

    override fun getRefreshKey(state: PagingState<Int, MovieUI>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieUI> {
        val page: Int = params.key ?: 1
        return try {
            val serverResponse = apiHolder.moviesApi.getSimilarMovies(
                movieId = movieId,
                language = "en-EN",
                page = page,
            ).results.map { it.toMovieUI() }
            val nextKey = if (serverResponse.isEmpty()) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(
                data = serverResponse,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}