package com.example.java.android1.movie_search.datasource.category

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

    override fun getCategoryMovies(categoryMovies: String): Flow<List<LocalMovieData>> {
        TODO("Not yet implemented")
    }
}