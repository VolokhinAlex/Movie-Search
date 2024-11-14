package com.volokhinaleksey.movie_club.datasource.category

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.datasource.pagesource.LocalCategoryPageSource
import com.volokhinaleksey.movie_club.model.local.LocalMovieData
import kotlinx.coroutines.flow.Flow

class LocalCategoryDataSourceImpl(
    private val db: MovieDataBase
) : LocalCategoryDataSource {
    override suspend fun saveMovie(movie: LocalMovieData) {
        TODO()
    }

    override fun getCategoryMovies(categoryMovies: String): Flow<PagingData<LocalMovieData>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                LocalCategoryPageSource(
                    db = db,
                    category = categoryMovies
                )
            }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}