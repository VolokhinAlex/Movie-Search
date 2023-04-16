package com.example.java.android1.movie_search.datasource.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.java.android1.movie_search.datasource.pagesource.LocalSearchPageSource
import com.example.java.android1.movie_search.model.local.LocalMovieData
import com.example.java.android1.movie_search.room.MoviesDataBase
import com.example.java.android1.movie_search.utils.mapLocalMovieToMovieEntity
import kotlinx.coroutines.flow.Flow

class LocalSearchDataSourceImpl(
    private val db: MoviesDataBase
) : LocalSearchDataSource {

    override suspend fun saveMovie(movie: LocalMovieData) {
        db.moviesDao().insert(mapLocalMovieToMovieEntity(movie = movie))
    }

    override fun getMoviesByQuery(query: String): Flow<PagingData<LocalMovieData>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                LocalSearchPageSource(
                    db = db,
                    query = query
                )
            }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}