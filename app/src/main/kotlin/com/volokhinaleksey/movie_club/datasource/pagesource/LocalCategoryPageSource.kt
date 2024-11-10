package com.volokhinaleksey.movie_club.datasource.pagesource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.volokhinaleksey.movie_club.database.room.MoviesDataBase
import com.volokhinaleksey.movie_club.model.local.LocalMovieData
import com.volokhinaleksey.movie_club.utils.mapMovieEntityToLocalMovieData
import kotlinx.coroutines.delay

class LocalCategoryPageSource(
    private val db: MoviesDataBase,
    private val category: String,
) : PagingSource<Int, LocalMovieData>() {

    override fun getRefreshKey(state: PagingState<Int, LocalMovieData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocalMovieData> {
        val page = params.key ?: 0
        return try {
            val movies =
                db.moviesDao().getMoviesByCategory(
                    limit = params.loadSize,
                    offset = page * params.loadSize,
                    category = category
                ).map {
                    mapMovieEntityToLocalMovieData(
                        movieEntity = it,
                        actors = db.actorDao().getActorsByMovieId(movieId = it.movieId ?: 0),
                        trailers = db.trailerDao().getTrailerById(movieId = it.movieId ?: 0)
                    )
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