package com.example.java.android1.movie_search.datasource.search

import com.example.java.android1.movie_search.model.local.LocalMovieData
import com.example.java.android1.movie_search.room.MoviesDataBase
import com.example.java.android1.movie_search.utils.mapLocalMovieToMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalSearchDataSourceImpl(
    private val db: MoviesDataBase
) : LocalSearchDataSource {
    override suspend fun saveMovie(movie: LocalMovieData) {
        db.moviesDao().insert(mapLocalMovieToMovieEntity(movie))
    }

    override fun getMoviesByQuery(query: String): Flow<List<LocalMovieData>> {
        return flow { db.moviesDao().getMoviesByQuery(query = query) }
    }

}