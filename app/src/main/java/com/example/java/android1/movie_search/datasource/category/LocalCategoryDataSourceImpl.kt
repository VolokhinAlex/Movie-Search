package com.example.java.android1.movie_search.datasource.category

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.java.android1.movie_search.datasource.pagesource.LocalCategoryPageSource
import com.example.java.android1.movie_search.model.local.LocalMovieData
import com.example.java.android1.movie_search.room.MoviesDataBase
import com.example.java.android1.movie_search.utils.mapLocalMovieToMovieEntity
import kotlinx.coroutines.flow.Flow

class LocalCategoryDataSourceImpl(
    private val db: MoviesDataBase
) : LocalCategoryDataSource {
    override suspend fun saveMovie(movie: LocalMovieData) {
        db.moviesDao().insert(mapLocalMovieToMovieEntity(movie))
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