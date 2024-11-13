package com.volokhinaleksey.movie_club.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.volokhinaleksey.movie_club.database.room.MoviesDataBase
import com.volokhinaleksey.movie_club.database.room.entity.toMovieUI
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import kotlinx.coroutines.delay

/**
 * Needed to navigate through the pages of the list
 * @param db - A database with methods for getting data from a local server
 * @param query - A request to be found and received from a local server
 */

class LocalSearchPageSource(
    private val db: MoviesDataBase,
    private val query: String,
) : PagingSource<Int, MovieUI>() {

    override fun getRefreshKey(state: PagingState<Int, MovieUI>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieUI> {
        val page = params.key ?: 0
        return try {
            val movies = db.moviesDao().getMoviesByQuery(
                limit = params.loadSize,
                offset = page * params.loadSize,
                query = query
            ).map {
                val actors = db.actorDao().getActorsByMovieId(movieId = it.movieId ?: 0)
                val trailers = db.trailerDao().getTrailerById(movieId = it.movieId ?: 0)
                it.toMovieUI(trailers, actors)
            }
            if (page != 0) delay(1000)
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}