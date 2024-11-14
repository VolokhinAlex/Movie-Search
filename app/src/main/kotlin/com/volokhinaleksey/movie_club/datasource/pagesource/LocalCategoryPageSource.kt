package com.volokhinaleksey.movie_club.datasource.pagesource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.model.local.LocalMovieData

class LocalCategoryPageSource(
    private val db: MovieDataBase,
    private val category: String,
) : PagingSource<Int, LocalMovieData>() {

    override fun getRefreshKey(state: PagingState<Int, LocalMovieData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocalMovieData> {
        TODO()
    }

}