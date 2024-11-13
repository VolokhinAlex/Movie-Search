package com.volokhinaleksey.movie_club.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.volokhinaleksey.movie_club.data.LocalSearchPageSource
import com.volokhinaleksey.movie_club.database.room.MoviesDataBase
import com.volokhinaleksey.movie_club.database.room.entity.asEntity
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import kotlinx.coroutines.flow.Flow

interface SearchDatabaseRepository : SearchRepository {
    suspend fun saveMovie(movie: MovieUI)
}

class SearchDatabaseRepositoryImpl(
    private val database: MoviesDataBase
) : SearchDatabaseRepository {

    override suspend fun saveMovie(movie: MovieUI) {
        database.moviesDao().insert(movie.asEntity(""))
    }

    override fun getMoviesByQuery(query: String): Flow<PagingData<MovieUI>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                LocalSearchPageSource(
                    db = database,
                    query = query
                )
            }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}